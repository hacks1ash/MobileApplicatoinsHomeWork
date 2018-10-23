package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signinMainActivityButton.setOnClickListener {
            checkAllFields()
        }
        registerMainActivityButton.setOnClickListener {
            registerFragment()
        }

    }

    private fun checkAllFields(){
        if (emailMainActivityEditText.text.toString().isEmpty() && passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter your email", Toast.LENGTH_SHORT).show()
        }else if(passwordMainActivityEditText.text.toString().isEmpty()){
            Toast.makeText(activity,"Please enter your password", Toast.LENGTH_SHORT).show()
        }else if(emailMainActivityEditText.text.toString().equals("email@example.com") && passwordMainActivityEditText.text.toString().equals("password")) {
            galleryFragment()
        }else{
            Toast.makeText(activity,"Email or Password is incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun galleryFragment() {
        val fragment = GalleryFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun registerFragment() {
        val fragment = RegisterFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}