package com.example.hackslash.finalproject

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

class LogInFragment : Fragment() {

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
        var valid: Boolean
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

    @Suppress("DEPRECATION")
    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(activity)
    }

    private fun showProgressDialog() {
        progressDialog.setMessage("Checking...")
        progressDialog.isIndeterminate = true
        progressDialog.show()

    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.hide()
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startRegisterFragment() {
        val fragment = RegisterFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    companion object {
        private const val TAG = "LogInFragment"
    }


}