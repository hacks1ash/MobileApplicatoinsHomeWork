package com.example.hackslash.finalproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val email: String, val displayName: String, val profileImageUrl: String) : Parcelable {
    constructor() : this("", "", "", "")
}