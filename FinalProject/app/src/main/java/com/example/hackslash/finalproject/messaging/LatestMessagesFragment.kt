package com.example.hackslash.finalproject.messaging

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import com.example.hackslash.finalproject.BaseFragment
import com.example.hackslash.finalproject.LatestMessageItem
import com.example.hackslash.finalproject.MainActivity
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.models.ChatMessage
import com.example.hackslash.finalproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_latest_messages.*

class LatestMessagesFragment : BaseFragment() {

    private val adapter = GroupAdapter<ViewHolder>()
    private lateinit var auth: FirebaseAuth
    private val latestMessagesMap = HashMap<String, ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_latest_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = "Home"

        latestMassagesRecycleView.adapter = adapter
        latestMassagesRecycleView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context, ChatActivity::class.java)

            val row = item as LatestMessageItem
            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        fetchCurrentUser()
        listenForLatestMessages()

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val reference = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshMessages()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshMessages()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun refreshMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageItem(it))
        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val reference = FirebaseDatabase.getInstance().getReference("/users/$uid")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.newMessageItemMessengerMenu -> {
                val intent = Intent(activity, NewMessageActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.messenger_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        var currentUser: User? = null
    }
}
