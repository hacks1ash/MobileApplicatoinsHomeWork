package com.example.hackslash.fragmentapp

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_firebase_emailpassword.*

class FireBaseEmailPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_emailpassword)

        emailCreateAccountButton.setOnClickListener {
            createAccount(fieldEmail.text.toString(), fieldPassword.text.toString())
            hideKeyboard(fieldPassword)
        }

        emailSignInButton.setOnClickListener {
            signIn(fieldEmail.text.toString(), fieldPassword.text.toString())
            hideKeyboard(fieldPassword)
        }

        signOutButton.setOnClickListener {
            signOut()
        }

        verifyEmailButton.setOnClickListener {
            sendEmailVerification()
        }


        //Firebase Init
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "Create Account : $email")
        if (!validForm()) {
            return
        }

        showProgressDialog()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Create User with Email : Success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d(TAG, "Create User with Email : Fail")
                    Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                hideProgressDialog()
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "Sign in : $email")
        if (!validForm()) {
            return
        }

        showProgressDialog()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Sign in with Email : Success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d(TAG, "Sign in with Email : Fail")
                    Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                hideProgressDialog()
            }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        verifyEmailButton.isEnabled = false

        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                verifyEmailButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Verification Email sent to : ${user.email} ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(baseContext, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendPasswordResetEmail() {
        val user = auth.currentUser
        if (user != null) {
            val userEmail = user.email

            userEmail?.let {
                auth.sendPasswordResetEmail(it)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email Sent")
                            Toast.makeText(baseContext, "Email Sent", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e(TAG, "sendPasswordResetLink", task.exception)
                            Toast.makeText(baseContext, "Email couldn't not be sent", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


    private fun validForm(): Boolean {
        var valid = true
        val email = fieldEmail.text.toString()
        if (email.isEmpty()) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (password.isEmpty()) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }


    private fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()
        if (user != null) {
            status.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            detail.text = getString(R.string.firebase_status_fmt, user.uid)
            emailPasswordButtons.visibility = View.GONE
            emailPasswordFields.visibility = View.GONE
            signedInButtons.visibility = View.VISIBLE
            verifyEmailButton.isEnabled = !user.isEmailVerified
        } else {
            status.setText(R.string.signed_out)
            detail.text = null
            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            signedInButtons.visibility = View.GONE
        }
    }

    @Suppress("DEPRECATION")
    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
    }

    private fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    companion object {
        private const val TAG = "Firebase EmailPassword"
    }
}