package com.example.hackslash.finalproject

import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_from.view.*

class ChatFromItem(val text: String, val profileUrl: String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.fromTextViewChatActivity.text = text
        Picasso.get().load(profileUrl).into(viewHolder.itemView.fromProfilePictureChatActivity)
    }

    override fun getLayout(): Int {
        return R.layout.message_from
    }
}