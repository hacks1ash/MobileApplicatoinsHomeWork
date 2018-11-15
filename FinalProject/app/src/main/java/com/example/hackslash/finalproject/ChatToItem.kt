package com.example.hackslash.finalproject

import com.example.hackslash.finalproject.messaging.LatestMessagesFragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_to.view.*

class ChatToItem(val text: String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(LatestMessagesFragment.currentUser?.profileImageUrl)
            .into(viewHolder.itemView.toProfilePictureChatActivity)
        viewHolder.itemView.toTextViewChatActivity.text = text
    }

    override fun getLayout(): Int {
        return R.layout.message_to
    }
}