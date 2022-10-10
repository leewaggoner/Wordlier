package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.utils.PreferencesWrapper

private const val PLAYER_NAME_TAG = "PlayerName"

class PlayerRepo(
    private val preferencesWrapper: PreferencesWrapper
    ) {
    fun setPlayerData(playerData: PlayerData) {
        preferencesWrapper.putString(PLAYER_NAME_TAG, playerData.name)
    }

    fun getPlayerData() : PlayerData {
        return PlayerData(preferencesWrapper.getString(PLAYER_NAME_TAG, ""))
    }
}