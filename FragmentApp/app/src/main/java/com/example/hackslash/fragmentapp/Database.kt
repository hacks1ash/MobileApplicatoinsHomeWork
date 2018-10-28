package com.example.hackslash.fragmentapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: User):Boolean {

        val db = writableDatabase
        val values = ContentValues()
        values.put(DatabaseInfo.UserEntry.COLUMN_USER_ID, user.userId)
        values.put(DatabaseInfo.UserEntry.COLUMN_FIRST_NAME, user.firstName)
        values.put(DatabaseInfo.UserEntry.COLUMN_LAST_NAME, user.lastName)
        values.put(DatabaseInfo.UserEntry.COLUMN_EMAIL, user.email)
        values.put(DatabaseInfo.UserEntry.COLUMN_PASSWORD, user.password)
        values.put(DatabaseInfo.UserEntry.COLUMN_LOGGED, user.logged)

        db.insert(DatabaseInfo.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(email: String): Boolean {
        val db = writableDatabase
        val selection = DatabaseInfo.UserEntry.COLUMN_EMAIL + " LIKE ?"
        val selectionArgs = arrayOf(email)
        db.delete(DatabaseInfo.UserEntry.TABLE_NAME, selection,selectionArgs)
        return true
    }

    fun signInUpdate(email:String):Boolean {
        val db =writableDatabase
        val values = ContentValues()
        values.put(DatabaseInfo.UserEntry.COLUMN_LOGGED, "true")
        val selection = DatabaseInfo.UserEntry.COLUMN_EMAIL + " =?"
        val selectionArgs = arrayOf(email)
        db.update(DatabaseInfo.UserEntry.TABLE_NAME,values,selection,selectionArgs)
        return true
    }

    fun signOutUpdate(email:String):Boolean {
        val db =writableDatabase
        val values = ContentValues()
        values.put(DatabaseInfo.UserEntry.COLUMN_LOGGED, "false")
        val selection = DatabaseInfo.UserEntry.COLUMN_EMAIL + " =?"
        val selectionArgs = arrayOf(email)
        db.update(DatabaseInfo.UserEntry.TABLE_NAME,values,selection,selectionArgs)
        return true
    }

    fun readUser(email:String):ArrayList<User>{
        val users = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DatabaseInfo.UserEntry.TABLE_NAME + " WHERE " + DatabaseInfo.UserEntry.COLUMN_EMAIL + "='" +email+"'",null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var userId:String
        var firstName:String
        var lastName:String
        var password:String
        var logged:String
        if(cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                userId = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_USER_ID))
                firstName = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_FIRST_NAME))
                lastName = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_LAST_NAME))
                password = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_PASSWORD))
                logged = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_LOGGED))

                users.add(User(userId.toInt(), firstName, lastName, email, password, logged))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUsers():ArrayList<User>{
        val users = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DatabaseInfo.UserEntry.TABLE_NAME, null)
        } catch (e :SQLiteException){
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var userId:String
        var firstName:String
        var lastName:String
        var email:String
        var password:String
        var logged:String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                userId = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_USER_ID))
                firstName = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_FIRST_NAME))
                lastName = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_LAST_NAME))
                email = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_PASSWORD))
                logged = cursor.getString(cursor.getColumnIndex(DatabaseInfo.UserEntry.COLUMN_LOGGED))

                users.add(User(userId.toInt(), firstName, lastName, email, password, logged))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FragmentApp.db"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + DatabaseInfo.UserEntry.TABLE_NAME + " (" +
                DatabaseInfo.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
                DatabaseInfo.UserEntry.COLUMN_FIRST_NAME +" TEXT," +
                DatabaseInfo.UserEntry.COLUMN_LAST_NAME + " TEXT," +
                DatabaseInfo.UserEntry.COLUMN_EMAIL + " TEXT," +
                DatabaseInfo.UserEntry.COLUMN_PASSWORD + " TEXT," +
                DatabaseInfo.UserEntry.COLUMN_LOGGED + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseInfo.UserEntry.TABLE_NAME
    }
}