package com.wreckingball.wordlier.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

private const val REPO_NAME = "com.wreckingball.wordlier"

class DataStoreWrapper(dataStore: DataStore<Preferences>) {
    private val store = dataStore

    private object PreferencesKey {
        val PLAYER_NAME_KEY = stringPreferencesKey("PlayerName")
    }

    suspend fun getPlayerName(default: String) : String {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.PLAYER_NAME_KEY] ?: default
    }

    suspend fun putPlayerName(name: String) {
        store.edit { preferences ->
            preferences[PreferencesKey.PLAYER_NAME_KEY] = name
        }
    }
}