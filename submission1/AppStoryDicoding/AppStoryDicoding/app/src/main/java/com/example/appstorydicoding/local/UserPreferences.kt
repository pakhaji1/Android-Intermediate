package com.example.appstorydicoding.local

import android.content.Context

internal class UserPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserToken){
        val editor = preferences.edit()
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): UserToken {
        val model = UserToken()
        model.token = preferences.getString(TOKEN, "")
        return model
    }

    companion object {
        private const val PREFS_NAME = "USER_PREF"
        private const val TOKEN = "TOKEN"
    }
}