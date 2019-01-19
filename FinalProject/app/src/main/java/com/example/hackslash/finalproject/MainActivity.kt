package com.example.hackslash.finalproject

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.example.hackslash.finalproject.messaging.LatestMessagesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.application_bar.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        updateUI(auth,currentUser)
    }

    override fun onBackPressed() {
        val currentFragment : Fragment = LatestMessagesFragment()

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }


        if (currentFragment.isVisible)
            Toast.makeText(this, "Match", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "No Match", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainMenuLoginRegisterItem -> {
                signInFragment()
            }

            R.id.mainMenuSignOutItem -> {
                signOut(auth)
            }

            R.id.mainMenuHome -> {
                messagesFragment()
            }

            R.id.mainMenuProfile -> {
                profileFragment()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
