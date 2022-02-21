package com.wreckingball.wordlier.utils

import android.content.SharedPreferences

class PreferencesWrapper(sharedPreferences: SharedPreferences) {
    private val preferences = sharedPreferences

    fun getString(key: String, default: String): String {
        return preferences.getString(key, default) ?: default
    }

    fun putString(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
            apply()
        }
    }
}
