package com.example.hackslash.finalproject

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.hackslash.finalproject.account.LogInFragment
import com.example.hackslash.finalproject.account.ProfileFragment
import com.example.hackslash.finalproject.messaging.LatestMessagesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.application_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            setSupportActionBar(toolbar)

            val toggle =
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation, R.string.close_navigation)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            if (savedInstanceState == null) {
                messagesFragment()
            }
        } else {
            signInFragment()
        }

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainMenuLoginRegisterItem -> {
                signInFragment()
            }

            R.id.mainMenuSignOutItem -> {
                signOut()
            }

            R.id.mainMenuHome -> {
                //removeFragments()
                messagesFragment()
            }

            R.id.mainMenuProfile -> {
                profileFragment()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
        recreate()
    }

    private fun updateUI(user: FirebaseUser?) {

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

    private fun signInFragment() {
        val fragment = LogInFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun profileFragment() {
        val fragment = ProfileFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun messagesFragment() {
        val fragment = LatestMessagesFragment()
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun removeFragments() {
        val manager = this.supportFragmentManager
        val transaction = manager.beginTransaction()
        supportFragmentManager.findFragmentById(R.id.contentConstraintLayout)?.let { transaction.remove(it) }
        transaction.commit()
    }
}
