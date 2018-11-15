package com.example.hackslash.finalproject

import com.example.hackslash.finalproject.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.contact_row.view.*

class UserItem(val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.contactUserNameTextViewNewMessage.text = user.displayName

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.contactProfilePicImageViewNewMessage)
    }

    override fun getLayout(): Int {
        return R.layout.contact_row
    }
}