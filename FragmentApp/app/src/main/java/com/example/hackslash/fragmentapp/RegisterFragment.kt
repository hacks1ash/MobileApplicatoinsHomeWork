package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_register.*

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

    private fun checkAllFields() {
        if (firstnameRegisterEditText.text.toString().isEmpty() && lastnameRegisterEditText.text.toString().isEmpty() && emailRegisterEditText.text.toString().isEmpty() && passwordRegisterEditText.text.toString().isEmpty() && repeatpasswordRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.all_fields_empty, Toast.LENGTH_SHORT).show()
        } else if (firstnameRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.first_name_empty, Toast.LENGTH_SHORT).show()
        } else if (lastnameRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.last_name_empty, Toast.LENGTH_SHORT).show()
        } else if (emailRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.email_empty, Toast.LENGTH_SHORT).show()
        } else if (passwordRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.password_empty, Toast.LENGTH_SHORT).show()
        } else if (repeatpasswordRegisterEditText.text.toString().isEmpty()) {
            Toast.makeText(activity, R.string.repeat_password_empty, Toast.LENGTH_SHORT).show()
        } else {
            if (Utils().isEmailValid(emailRegisterEditText.text.toString())) {
                if (passwordRegisterEditText.text.toString().equals(repeatpasswordRegisterEditText.text.toString())) {
                    register()
                    signInFragment()
                } else {
                    Toast.makeText(activity, R.string.password_no_match, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, R.string.invalid_email, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInFragment() {
        val fragment = SignInFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun register() {
        var firstName = firstnameRegisterEditText.text.toString()
        var lastName = lastnameRegisterEditText.text.toString()
        val email = emailRegisterEditText.text.toString()
        val password = passwordRegisterEditText.text.toString()
        Database(this.activity!!).insertUser(
            User(
                null,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                logged = "false"
            )
        )
    }
}