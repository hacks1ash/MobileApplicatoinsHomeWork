package com.example.hackslash.finalproject.account

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hackslash.finalproject.MainActivity
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.Utils
import com.example.hackslash.finalproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        activity?.title = ""

        createAccountRegisterActivityButton.setOnClickListener {
            createAccount(
                emailRegisterActivityEditText.text.toString(),
                passwordRegisterActivityEditText.text.toString()
            )
        }

        alreadyAccountRegisterActivityButton.setOnClickListener {
            startLogInFragment()
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

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "Create Account : $email")
        if (!validInputs()) {
            return
        }

        showProgressDialog()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Create User with Email : Success")
                    setNewUserName()
                } else {
                    Log.d(TAG, "Create User with Email : Fail")
                    Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }

                hideProgressDialog()
            }
    }

    private fun setNewUserName() {
        val name = nameRegisterActivityEditText.text.toString()
        val user = auth.currentUser
        val setProfileSetting = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        user?.updateProfile(setProfileSetting)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "UserNameSet:Success")
                    saveUserInfoToFirebaseDatabase()
                    startMainActivity()
                } else {
                    Log.d(TAG, "UserNameSet:Failed")
                    Toast.makeText(activity, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun validInputs(): Boolean {
        var valid = true
        val name = nameRegisterActivityEditText.text.toString()
        val email = emailRegisterActivityEditText.text.toString()
        val password = passwordRegisterActivityEditText.text.toString()
        val repeatPassword = repeatPasswordRegisterActivityEditText.text.toString()
        if (email.isNotEmpty() && Utils().isEmailValid(email)) {
            emailRegisterActivityEditText.error = null
        } else if (email.isEmpty()) {
            emailRegisterActivityEditText.error = "Required!"
            valid = false
        } else if (!Utils().isEmailValid(email)) {
            emailRegisterActivityEditText.error = "Email not valid!"
        }

        if (password.isNotEmpty() && repeatPassword.isNotEmpty() && password == repeatPassword) {
            passwordRegisterActivityEditText.error = null
            repeatPasswordRegisterActivityEditText.error = null
        } else if (password.isEmpty() && repeatPassword.isEmpty()) {
            passwordRegisterActivityEditText.error = "Required!"
            repeatPasswordRegisterActivityEditText.error = "Required!"
            valid = false
        } else if (password.isEmpty()) {
            passwordRegisterActivityEditText.error = "Required!"
            valid = false
        } else if (repeatPassword.isEmpty()) {
            repeatPasswordRegisterActivityEditText.error = "Required!"
            valid = false
        } else if (password != repeatPassword) {
            passwordRegisterActivityEditText.error = "Passwords do not match!"
            repeatPasswordRegisterActivityEditText.error = "Passwords do not match!"
            valid = false
        }

        if (name.isEmpty()) {
            nameRegisterActivityEditText.error = "Required!"
            valid = false
        } else {
            nameRegisterActivityEditText.error = null
        }

        return valid
    }

    private fun saveUserInfoToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email
        val displayName = FirebaseAuth.getInstance().currentUser?.displayName
        val defaultProfilePic = "https://firebasestorage.googleapis.com/v0/b/hackslash-final.appspot.com/o/images%2Fuser-icon-png-person-user-profile-icon-20.png?alt=media&token=97bdfad1-41b0-40f0-ac26-6c05d1fb6c2f"
        val reference = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid!!, email!!, displayName!!, defaultProfilePic)
        reference.setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Saved user to Database")
                }
            }

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

    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startLogInFragment() {
        val fragment = LogInFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    companion object {
        private const val TAG = "RegisterFragment"
    }
}