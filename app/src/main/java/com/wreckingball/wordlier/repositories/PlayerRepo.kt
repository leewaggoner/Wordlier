package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.utils.DataStoreWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepo(private val dataStore: DataStoreWrapper) {
    suspend fun setPlayerName(playerData: PlayerData) = withContext(Dispatchers.IO) {
        dataStore.putPlayerName(playerData.name)
    }

    suspend fun getPlayerName() = withContext(Dispatchers.IO) {
        dataStore.getPlayerName("")
    }
}