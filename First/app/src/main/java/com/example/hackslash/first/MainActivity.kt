package com.example.hackslash.first

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Check.setOnClickListener {
            editText = findViewById<View>(R.id.email) as EditText
            val email = editText.text.toString()
            editText = findViewById<View>(R.id.password) as EditText
            val password = editText.text.toString()

            if (email == "email@example.com" && password == "password") {
                val intent = Intent(this, activity_galley::class.java)
                startActivity(intent)
            }
        }
    }
}
