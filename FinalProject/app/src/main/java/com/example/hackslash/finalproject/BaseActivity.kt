package com.example.hackslash.finalproject

import android.content.Context
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.hackslash.finalproject.account.LogInFragment
import com.example.hackslash.finalproject.account.ProfileFragment
import com.example.hackslash.finalproject.messaging.LatestMessagesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class BaseActivity : AppCompatActivity() {

    fun signOut(auth: FirebaseAuth) {
        auth.signOut()
        updateUI(auth, null)
        finish()
    }

    fun updateUI(auth: FirebaseAuth, user: FirebaseUser?) {

        val currentUser = auth.currentUser
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        val hView: View = navigationView.getHeaderView(0)
        val userEmail: TextView = hView.findViewById(R.id.userEmailNavigationHeader)
        val userName: TextView = hView.findViewById(R.id.userNameNavigationHeader)

        if (user != null) {
            val email = currentUser!!.email
            val name = currentUser.displayName
            userName.text = name
            userEmail.text = email
            showMenuItem(R.id.mainMenuSignOutItem)
            hideMenuItem(R.id.mainMenuLoginRegisterItem)
        } else {
            userName.text = ""
            userEmail.text = ""
            showMenuItem(R.id.mainMenuLoginRegisterItem)
            hideMenuItem(R.id.mainMenuSignOutItem)
        }
    }

    private fun hideMenuItem(menuItemID: Int) {
        val navigation: NavigationView = findViewById(R.id.navigationView)
        val menu = navigation.menu
        menu.findItem(menuItemID).isVisible = false
    }

    private fun showMenuItem(id: Int) {
        val navigation: NavigationView = findViewById(R.id.navigationView)
        val menu = navigation.menu
        menu.findItem(id).isVisible = true
    }

    fun signInFragment() {
        val fragment = LogInFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun profileFragment() {
        val fragment = ProfileFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun messagesFragment() {
        val fragment = LatestMessagesFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun removeFragments() {
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        supportFragmentManager.findFragmentById(R.id.contentConstraintLayout)?.let { transaction.remove(it) }
        transaction.commit()
    }

    fun hideKeyboard(view: View) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}