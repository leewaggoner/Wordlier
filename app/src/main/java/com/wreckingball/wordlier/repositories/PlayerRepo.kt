package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.utils.DataStoreWrapper
import kotlinx.coroutines.flow.Flow

class PlayerRepo(/*private val preferencesWrapper: PreferencesWrapper*/ private val dataStore: DataStoreWrapper) {
    suspend fun setPlayerName(playerData: PlayerData) {
        dataStore.putPlayerName(playerData.name)
//        preferencesWrapper.putString(PLAYER_NAME_TAG, playerData.name)
    }

    suspend fun getPlayerName() : String {
        return dataStore.getPlayerName("")
//        return PlayerData(preferencesWrapper.getString(PLAYER_NAME_TAG, ""))
    }
}