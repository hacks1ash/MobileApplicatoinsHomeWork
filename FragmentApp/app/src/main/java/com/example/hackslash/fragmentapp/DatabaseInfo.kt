package com.example.hackslash.fragmentapp

import android.provider.BaseColumns

object DatabaseInfo {
    class UserEntry: BaseColumns {
        companion object {
            const val TABLE_NAME = "users"
            const val COLUMN_USER_ID = "user_id"
            const val COLUMN_FIRST_NAME = "first_name"
            const val COLUMN_LAST_NAME = "last_name"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
            const val COLUMN_LOGGED = "logged"
        }
    }
}