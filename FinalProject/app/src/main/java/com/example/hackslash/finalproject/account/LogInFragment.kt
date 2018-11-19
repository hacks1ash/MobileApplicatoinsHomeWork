package com.example.hackslash.finalproject.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.hackslash.finalproject.BaseFragment
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

class LogInFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        activity?.title = ""

        logInSignInActivityButton.setOnClickListener {
            hideKeyboard(passwordSignInActivityEditText)
            signIn(emailSignInActivityEditText.text.toString(), passwordSignInActivityEditText.text.toString())
        }

        createAccountSignInActivityButton.setOnClickListener {
            hideKeyboard(passwordSignInActivityEditText)
            startRegisterFragment()
        }

        resetPasswordSignInActivityButton.setOnClickListener {
            logInSignInActivityLayout.visibility = View.GONE
            resetPasswordSignInActivityLayout.visibility = View.VISIBLE
            hideKeyboard(passwordSignInActivityEditText)
        }

        resetPasswordButton.setOnClickListener {
            hideKeyboard(emailResetPassSignInActivityEditText)
            resetPassword(emailResetPassSignInActivityEditText)
        }

        backToSignInActivityButton.setOnClickListener {
            resetPasswordSignInActivityLayout.visibility = View.GONE
            logInSignInActivityLayout.visibility = View.VISIBLE
        }

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startMainActivity()
        }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "Sign in : $email")
        if (!validInputs()) {
            return
        }

        showProgressDialog()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this.activity!!) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Sign in with Email : Success")
                startMainActivity()
            } else {
                Log.d(TAG, "Sign in with Email : Fail")
                Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }

            hideProgressDialog()
        }
    }

    private fun resetPassword(email: EditText) {
        val resetEmail = email.text.toString()
        Log.d(TAG, "Password reset : $resetEmail")

        if (!emailValid()) {
            return
        }

        showProgressDialog()

        auth.sendPasswordResetEmail(resetEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email Sent!")
                    Toast.makeText(activity, "Reset email sent!", Toast.LENGTH_SHORT).show()
                    resetPasswordSignInActivityLayout.visibility = View.GONE
                    logInSignInActivityLayout.visibility = View.VISIBLE
                    email.text.clear()
                } else {
                    Log.d(TAG, "Email Failed")
                    Toast.makeText(activity, "No user found with this email!", Toast.LENGTH_SHORT).show()
                    email.text.clear()
                }

                hideProgressDialog()
            }

    }

    private fun emailValid(): Boolean {
        val valid: Boolean
        val email = emailResetPassSignInActivityEditText.text.toString()

        if (email.isEmpty()) {
            emailResetPassSignInActivityEditText.error = "Required!"
            valid = false
        } else if (email.isNotEmpty() && !Utils().isEmailValid(email)) {
            emailResetPassSignInActivityEditText.error = "Invalid email!"
            valid = false
        } else {
            valid = true
        }

        return valid
    }

    private fun validInputs(): Boolean {
        var valid = true
        val email = emailSignInActivityEditText.text.toString()
        val password = passwordSignInActivityEditText.text.toString()
        if (email.isNotEmpty() && Utils().isEmailValid(email)) {
            emailSignInActivityEditText.error = null
        } else if (email.isEmpty()) {
            emailSignInActivityEditText.error = "Required"
            valid = false
        } else if (!Utils().isEmailValid(email)) {
            emailSignInActivityEditText.error = "Email not valid"
        }

        if (password.isNotEmpty())
            passwordSignInActivityEditText.error = null
        else {
            passwordSignInActivityEditText.error = "Required."
            valid = false
        }

        return valid
    }

    companion object {
        private const val TAG = "LogInFragment"
    }


}