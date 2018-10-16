package com.example.hackslash.first

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerActivityButton.setOnClickListener {
            checkallfields()
        }
    }

    private fun checkallfields(){
        if (firstnameRegisterEditText.text.toString().isEmpty() && lastnameRegisterEditText.text.toString().isEmpty() && emailRegisterEditText.text.toString().isEmpty() && passwordRegisterEditText.text.toString().isEmpty() && repeatpasswordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(firstnameRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter first name", Toast.LENGTH_SHORT).show()
        }else if(lastnameRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter last name", Toast.LENGTH_SHORT).show()
        }else if(emailRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
        }else if(passwordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show()
        }else if(repeatpasswordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Please repeat password", Toast.LENGTH_SHORT).show()
        } else {
            if (Tools.isEmailValid(emailRegisterEditText.text.toString())){
                if(passwordRegisterEditText.text.toString().equals(repeatpasswordRegisterEditText.text.toString())) {
                    success()
                }else{
                    Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Please enter valid email", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun success() {
        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
    }
}
