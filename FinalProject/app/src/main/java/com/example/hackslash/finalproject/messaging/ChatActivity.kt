package com.example.hackslash.finalproject.messaging

import android.os.Bundle
import android.util.Log
import com.example.hackslash.finalproject.BaseActivity
import com.example.hackslash.finalproject.ChatFromItem
import com.example.hackslash.finalproject.ChatToItem
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.models.ChatMessage
import com.example.hackslash.finalproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseActivity() {

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        getMessages()

        recycleViewChatActivity.adapter = adapter

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = user.displayName

        sendButtonChatActivity.setOnClickListener {
            sendMessage()
            hideKeyboard(messageEditTextChatActivity)
        }
    }

    private fun getMessages() {
        val userTo = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val from = FirebaseAuth.getInstance().uid
        val to = userTo.uid

        val reference = FirebaseDatabase.getInstance().getReference("/messages/$from/$to")


        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.message)
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatToItem(chatMessage.message))
                    } else {
                        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
                        adapter.add(ChatFromItem(chatMessage.message, user.profileImageUrl))
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.message)
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
                        adapter.add(ChatFromItem(chatMessage.message, user.profileImageUrl))

                    } else {
                        adapter.add(ChatToItem(chatMessage.message))
                    }
                }
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })

    }


    private fun sendMessage() {
        val text = messageEditTextChatActivity.text.toString()

        val from = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val to = user.uid

        if (from == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages/$from/$to").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/messages/$to/$from").push()

        val chatMessage = ChatMessage(reference.key!!, text, from, to, System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage)
            .addOnCompleteListener {
                Log.d(TAG, "Message Sent!")
                messageEditTextChatActivity.text.clear()
                recycleViewChatActivity.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)
            .addOnCompleteListener {
                Log.d(TAG, "Message Sent!")
                messageEditTextChatActivity.text.clear()
                recycleViewChatActivity.scrollToPosition(adapter.itemCount - 1)
            }
        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$from/$to")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$to/$from")
        latestMessageToRef.setValue(chatMessage)
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}
