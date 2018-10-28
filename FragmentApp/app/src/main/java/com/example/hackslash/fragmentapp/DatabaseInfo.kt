package com.example.hackslash.fragmentapp

import android.provider.BaseColumns

object DatabaseInfo {
    class UserEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_USER_ID = "user_id"
            val COLUMN_FIRST_NAME = "first_name"
            val COLUMN_LAST_NAME = "last_name"
            val COLUMN_EMAIL = "email"
            val COLUMN_PASSWORD = "password"
            val COLUMN_LOGGED = "logged"
        }
    }
}