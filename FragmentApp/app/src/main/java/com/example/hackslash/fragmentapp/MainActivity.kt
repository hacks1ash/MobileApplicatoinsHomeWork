package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.application_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            signInFragment()
        }
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
            R.id.mainMenuGalleyItem -> {
                galleryFragment()
            }

            R.id.mainMenuResgiterItem -> {
                registerFragment()
            }

            R.id.mainMenuSignInItem -> {
                signInFragment()
            }

            R.id.mainMenuGameItem -> {
                gameFragment()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun galleryFragment() {
        val fragment = GalleryFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun signInFragment() {
        val fragment = SignInFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun registerFragment() {
        val fragment = RegisterFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun gameFragment() {
        val fragment = GameFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.contentConstraintLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
