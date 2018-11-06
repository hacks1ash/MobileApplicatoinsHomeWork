package com.example.hackslash.fragmentapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_data_admin.*

class AdminFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_data_admin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_add_user.setOnClickListener {
            addUser()
        }
        button_show_all.setOnClickListener {
            showAllUsers()
        }
        button_delete_user.setOnClickListener {
            deleteUser()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addUser() {
        val firstName = edittext_first_name.text.toString()
        val lastName = edittext_last_name.text.toString()
        val email = edittext_email.text.toString()
        val password = edittext_password.text.toString()
        val result = Database(this.activity!!).insertUser(
            User(
                null,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                logged = "false"
            )
        )

        edittext_first_name.setText("")
        edittext_last_name.setText("")
        edittext_email.setText("")
        edittext_password.setText("")
        textview_result.text = "Added user: $result"
        ll_entries.removeAllViews()
    }

    @SuppressLint("SetTextI18n")
    private fun showAllUsers() {
        val users = Database(this.activity!!).readAllUsers()
        ll_entries.removeAllViews()
        users.forEach {
            val tvUser = TextView(activity)
            tvUser.textSize = 30F
            tvUser.text = it.userId.toString() + " " + it.firstName + " " + it.lastName + " " + it.email + " " +
                    it.password + " " + it.logged
            ll_entries.addView(tvUser)
        }
        textview_result.text = "Fetched " + users.size + " users"
    }

    @SuppressLint("SetTextI18n")
    private fun showUserByEmail(email: String) {
        val users = Database(this.activity!!).readUser(email)
        ll_entries.removeAllViews()
        users.forEach {
            val tvUser = TextView(activity)
            tvUser.textSize = 30F
            tvUser.text = it.userId.toString() + " " + it.firstName + " " + it.lastName + " " + it.email + " " +
                    it.password + " " + it.logged
            ll_entries.addView(tvUser)
        }
        textview_result.text = "Fetched " + users.size + " users"
    }

    private fun deleteUser() {
        val email = edittext_email.text.toString()
        Database(this.activity!!).deleteUser(email)
    }
}