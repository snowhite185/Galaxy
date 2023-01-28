package com.example.galaxy.utils

import android.content.Context

class PrefUtil(context: Context) {

    private val pref = context.getSharedPreferences("galaxy", Context.MODE_PRIVATE)

    fun getUserName(): String {
        return getString(PrefKeys.USER_NAME)
    }

    fun saveUserName(name: String) {
        save(PrefKeys.USER_NAME, name)
    }

    fun isEmpty(key: String): Boolean {
        return pref.getString(key, "")?.isEmpty() ?: true
    }

    fun save(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return pref.getString(key, "") ?: ""
    }
}

object PrefKeys {
    const val USER_NAME = "key_user_name"
}