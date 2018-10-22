package com.example.hackslash.first

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*

class NavigationDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_gallery -> {
                gallery()
            }
            R.id.nav_sign_in -> {
                signin()
            }
            R.id.nav_sign_up -> {
                register()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun gallery() {
        val galleryActivity = Intent(this, activity_galley::class.java)
        startActivity(galleryActivity)
    }
    fun register(){
        val registerActivity = Intent(this, RegisterActivity::class.java)
        startActivity(registerActivity)
    }
    fun signin() {
        val signinActivity = Intent(this, MainActivity::class.java)
        startActivity(signinActivity)
    }
}
