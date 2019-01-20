package com.example.hackslash.finalproject.messaging

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.hackslash.finalproject.BaseActivity
import com.example.hackslash.finalproject.OnSwipeTouchListener_
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.UserItem
import com.example.hackslash.finalproject.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        this.title = "Select Contact"

        getAllContacts()

//        recycleViewNewMessageActivity.setOnTouchListener(object : OnSwipeTouchListener() {
//            override fun onSwipeRight() {
////
//                super.onSwipeRight()
//                onBackPressed()
//
//            }
//        })

        initSwipeSavedSearchesRecyclerView()


        recycleViewNewMessageActivity.setOnTouchListener(object : OnSwipeTouchListener_(this) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                onBackPressed()
            }
        })
    }


    private fun initSwipeSavedSearchesRecyclerView() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    if (direction == ItemTouchHelper.RIGHT) {
                        onBackPressed()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {


                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycleViewNewMessageActivity)
    }

    private fun getAllContacts() {
        val reference = FirebaseDatabase.getInstance().getReference("/users")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    it.toString()
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()
                }

                recycleViewNewMessageActivity.layoutManager = LinearLayoutManager(this@NewMessageActivity)
                recycleViewNewMessageActivity.setHasFixedSize(true)
                recycleViewNewMessageActivity.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    companion object {
        const val USER_KEY = "USER_KEY"
    }

}


