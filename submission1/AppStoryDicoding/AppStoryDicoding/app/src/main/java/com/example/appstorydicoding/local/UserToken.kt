package com.example.appstorydicoding.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserToken(
    var token: String? = ""
): Parcelable