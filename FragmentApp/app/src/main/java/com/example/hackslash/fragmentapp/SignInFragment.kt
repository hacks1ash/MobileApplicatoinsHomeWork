package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    private lateinit var userPassword: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signInMainActivityButton.setOnClickListener {
            checkAllFields()
        }
        registerMainActivityButton.setOnClickListener {
            registerFragment()
        }

    }


    private fun checkAllFields() {
        if (emailMainActivityEditText.text.toString().isEmpty() && passwordMainActivityEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.all_fields_empty, Toast.LENGTH_SHORT).show()
        } else if (emailMainActivityEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.email_empty, Toast.LENGTH_SHORT).show()
        } else if (passwordMainActivityEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.password_empty, Toast.LENGTH_SHORT).show()
        } else if (Utils().isEmailValid(emailMainActivityEditText.text.toString())) {
            if (checkCredentials(
                    emailMainActivityEditText.text.toString(),
                    passwordMainActivityEditText.text.toString()
                )
            ) {
                loggedIn(emailMainActivityEditText.text.toString())
                galleryFragment()
            }
        } else if (!Utils().isEmailValid(emailMainActivityEditText.text.toString())) {
            Toast.makeText(activity, R.string.invalid_email, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, R.string.incorrect_authorization, Toast.LENGTH_SHORT).show()
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

    private fun checkCredentials(email: String, password: String): Boolean {
        val users = Database(this.activity!!).readUser(email)
        users.forEach {
            userPassword = it.password
        }
        return password == userPassword
    }

    private fun loggedIn(email: String) {
        Database(this.activity!!).signInUpdate(email)
    }
}