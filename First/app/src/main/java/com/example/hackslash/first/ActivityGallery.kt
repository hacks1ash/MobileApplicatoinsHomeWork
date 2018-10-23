package com.example.hackslash.first

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_galley.*

class ActivityGallery : AppCompatActivity() {

    private var photo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galley)

        photo_activity_gallery.setImageResource(R.mipmap.mario)

        photobutton.setOnClickListener{
            if (photo){
                photo_activity_gallery.setImageResource(R.mipmap.mario)
                photo = false
            }
            else{
                photo_activity_gallery.setImageResource(R.mipmap.dino)
                photo = true
            }
        }

    }
}
