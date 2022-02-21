package com.wreckingball.wordlier.repositories

import android.content.Context
import com.wreckingball.wordlier.R

private const val REPO_NAME = "com.wreckingball.wordlier"
private const val PLAYER_NAME_TAG = "PlayerName"

class PlayerRepo(private val context: Context) {
    private val prefs = context.getSharedPreferences(REPO_NAME, Context.MODE_PRIVATE)
    fun setPlayerName(name: String) {
        prefs.edit().putString(PLAYER_NAME_TAG, name).apply()
    }

    fun getPlayerName() : String {
        val default = context.getString(R.string.player_name_unknown)
        return prefs.getString(PLAYER_NAME_TAG, default) ?: default
    }
}