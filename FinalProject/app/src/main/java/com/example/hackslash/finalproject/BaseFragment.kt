package com.example.hackslash.finalproject

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.hackslash.finalproject.account.LogInFragment
import com.example.hackslash.finalproject.account.RegisterFragment
import com.example.hackslash.finalproject.messaging.LatestMessagesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.*


open class BaseFragment : Fragment() {

    @Suppress("DEPRECATION")
    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(activity)
    }

    fun showProgressDialog() {
        progressDialog.setMessage("Checking...")
        progressDialog.isIndeterminate = true
        progressDialog.show()

    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.hide()
    }

    fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    fun startRegisterFragment() {
        val fragment = RegisterFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun messagesFragment() {
        val fragment = LatestMessagesFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun startLogInFragment() {
        val fragment = LogInFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun updateUI(auth: FirebaseAuth, user: FirebaseUser?) {
        val currentUser = auth.currentUser

        if (user != null) {
            val email = currentUser!!.email
            val name = currentUser.displayName
            userNameProfileFragmentTextView.text = name
            emailProfileFragmentTextView.text = email
        } else {
            userNameProfileFragmentTextView.text = ""
            emailProfileFragmentTextView.text = ""
        }
    }
}