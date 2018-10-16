package com.example.hackslash.first

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
    }
    private fun authorize() {
        val gallery = Intent(this, activity_galley::class.java)
        startActivity(gallery)
    }
    private fun register(){
        val register = Intent(this, RegisterActivity::class.java)
        startActivity(register)
    }

    private fun checkallfields(){
        if (emailMainActivityEditText.text.toString().isEmpty() && passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter your email", Toast.LENGTH_SHORT).show()
        }else if(passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().equals("email@example.com") && passwordMainActivityEditText.text.toString().equals("password")) {
            authorize()
        }else{
            Toast.makeText(this,"Email or Password is incorrect", Toast.LENGTH_SHORT).show()
        }
    }

}