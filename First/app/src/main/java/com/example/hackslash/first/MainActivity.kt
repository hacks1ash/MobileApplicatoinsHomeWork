package com.example.hackslash.first

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Check.setOnClickListener {
            authorize()
        }
    }
    private fun authorize() {
        if (email.text.toString().equals("email@example.com") && password.text.toString().equals("password")) {
            val intent = Intent(this, activity_galley::class.java)
            startActivity(intent)
        }
    }
}

