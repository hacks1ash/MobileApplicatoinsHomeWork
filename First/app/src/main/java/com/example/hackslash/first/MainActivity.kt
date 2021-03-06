package com.example.hackslash.first

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signinMainActivityButton.setOnClickListener {
            checkallfields()
        }

        registerMainActivityButton.setOnClickListener{
            register()
        }

        navigationdrawerButton.setOnClickListener {
            navigation()
        }
    }

    private fun checkallfields(){
        if (emailMainActivityEditText.text.toString().isEmpty() && passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter your email", Toast.LENGTH_SHORT).show()
        }else if(passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().equals("email@example.com") && passwordMainActivityEditText.text.toString().equals("password")) {
            gallery()
        }else{
            Toast.makeText(this,"Email or Password is incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gallery() {
        val galleryActivity = Intent(this, ActivityGallery::class.java)
        startActivity(galleryActivity)
    }
    private fun register(){
        val registerActivity = Intent(this, RegisterActivity::class.java)
        startActivity(registerActivity)
    }
    private fun navigation(){
        val navigationActivity = Intent(this, NavigationDrawer::class.java)
        startActivity(navigationActivity)
    }

}