package com.wreckingball.wordlier.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wreckingball.wordlier.domain.MAX_GUESSES
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

private const val REPO_NAME = "com.wreckingball.wordlier"

class DataStoreWrapper(dataStore: DataStore<Preferences>) {
    private val store = dataStore

    private object PreferencesKey {
        val PLAYER_NAME_KEY = stringPreferencesKey("PlayerName")
        val LAST_DATE_PLAYED = stringPreferencesKey("LastDatePlayed")
        val STREAK = intPreferencesKey("Streak")
        val MAX_STREAK = intPreferencesKey("MaxStreak")
        val ROUND_1_WINS = intPreferencesKey("Round1Wins")
        val ROUND_2_WINS = intPreferencesKey("Round2Wins")
        val ROUND_3_WINS = intPreferencesKey("Round3Wins")
        val ROUND_4_WINS = intPreferencesKey("Round4Wins")
        val ROUND_5_WINS = intPreferencesKey("Round5Wins")
        val ROUND_6_WINS = intPreferencesKey("Round6Wins")
        val TOTAL_WINS = intPreferencesKey("TotalWins")
        val TOTAL_LOSSES = intPreferencesKey("TotalLosses")
        val LAST_ROUND_WON = intPreferencesKey("LastRoundWon")
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

    suspend fun getLastDatePlayed(default: String) : String {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.LAST_DATE_PLAYED] ?: default
    }

    suspend fun putLastDatePlayed(lastDatePlayed: String) {
        store.edit { preferences ->
            preferences[PreferencesKey.LAST_DATE_PLAYED] = lastDatePlayed
        }
    }

    suspend fun getStreak(default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.STREAK] ?: default
    }

    suspend fun putStreak(streak: Int) {
        store.edit { preferences ->
            preferences[PreferencesKey.STREAK] = streak
        }
    }

    suspend fun getAllRoundWins(default: Int) : List<Int> {
        val result = mutableListOf<Int>()
        for (i in 0 until MAX_GUESSES) {
            result.add(getRoundWins(i, default))
        }
        return result
    }
    suspend fun getRoundWins(round: Int, default: Int) : Int {
        return when (round) {
            0 -> getRoundWinsData(PreferencesKey.ROUND_1_WINS, default)
            1 -> getRoundWinsData(PreferencesKey.ROUND_2_WINS, default)
            2 -> getRoundWinsData(PreferencesKey.ROUND_3_WINS, default)
            3 -> getRoundWinsData(PreferencesKey.ROUND_4_WINS, default)
            4 -> getRoundWinsData(PreferencesKey.ROUND_5_WINS, default)
            5 -> getRoundWinsData(PreferencesKey.ROUND_6_WINS, default)
            else -> default
        }
    }

    private suspend fun getRoundWinsData(round: Preferences.Key<Int>, default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[round] ?: default
    }

    suspend fun putRoundWins(round: Int, wins: Int) {
        when (round) {
            0 -> putRoundWinData(PreferencesKey.ROUND_1_WINS, wins)
            1 -> putRoundWinData(PreferencesKey.ROUND_2_WINS, wins)
            2 -> putRoundWinData(PreferencesKey.ROUND_3_WINS, wins)
            3 -> putRoundWinData(PreferencesKey.ROUND_4_WINS, wins)
            4 -> putRoundWinData(PreferencesKey.ROUND_5_WINS, wins)
            5 -> putRoundWinData(PreferencesKey.ROUND_6_WINS, wins)
            else -> { }
        }
    }

    private suspend fun putRoundWinData(round: Preferences.Key<Int>, wins: Int) {
        store.edit { preferences ->
            preferences[round] = wins
        }
    }

    suspend fun getTotalWins(default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.TOTAL_WINS] ?: default
    }

    suspend fun putTotalWins(wins: Int) {
        store.edit { preferences ->
            preferences[PreferencesKey.TOTAL_WINS] = wins
        }
    }

    suspend fun getTotalLosses(default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.TOTAL_LOSSES] ?: default
    }

    suspend fun putTotalLosses(losses: Int) {
        store.edit { preferences ->
            preferences[PreferencesKey.TOTAL_LOSSES] = losses
        }
    }

    suspend fun getMaxStreak(default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.MAX_STREAK] ?: default
    }

    suspend fun putMaxStreak(maxStreak: Int) {
        store.edit { preferences ->
            preferences[PreferencesKey.MAX_STREAK] = maxStreak
        }
    }

    suspend fun getLastRoundWon(default: Int) : Int {
        return store.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.LAST_ROUND_WON] ?: default
    }

    suspend fun putLastRoundWon(round: Int) {
        store.edit { preferences ->
            preferences[PreferencesKey.LAST_ROUND_WON] = round
        }
    }

    suspend fun clearAll() {
        store.edit {
            it.clear()
        }
    }
}