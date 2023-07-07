package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.utils.DataStoreWrapper

class PlayerRepo(private val dataStore: DataStoreWrapper) {
    suspend fun setPlayerName(playerData: PlayerData) {
        dataStore.putPlayerName(playerData.name)
    }

    suspend fun getPlayerName() : String {
        return dataStore.getPlayerName("")
    }
}