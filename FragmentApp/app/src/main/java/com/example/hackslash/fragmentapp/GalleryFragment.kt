package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private var photo: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        photo_activity_gallery.setImageResource(R.mipmap.lc_mario)

        photobutton.setOnClickListener {
            if (photo) {
                photo_activity_gallery.setImageResource(R.mipmap.lc_mario)
                photo = false
            } else {
                photo_activity_gallery.setImageResource(R.mipmap.lc_dino)
                photo = true
            }
        }
    }

}