package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerActivityButton.setOnClickListener {
            checkAllFields()
        }
    }

    private fun checkAllFields(){
        if (firstnameRegisterEditText.text.toString().isEmpty() && lastnameRegisterEditText.text.toString().isEmpty() && emailRegisterEditText.text.toString().isEmpty() && passwordRegisterEditText.text.toString().isEmpty() && repeatpasswordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(firstnameRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter first name", Toast.LENGTH_SHORT).show()
        }else if(lastnameRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter last name", Toast.LENGTH_SHORT).show()
        }else if(emailRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter email", Toast.LENGTH_SHORT).show()
        }else if(passwordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter password", Toast.LENGTH_SHORT).show()
        }else if(repeatpasswordRegisterEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please repeat password", Toast.LENGTH_SHORT).show()
        } else {
            if (isEmailValid(emailRegisterEditText.text.toString())){
                if(passwordRegisterEditText.text.toString().equals(repeatpasswordRegisterEditText.text.toString())) {
                    signInFragment()
                }else{
                    Toast.makeText(activity,"Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity,"Please enter valid email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun signInFragment() {
        val fragment = SignInFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}