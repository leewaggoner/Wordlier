package com.wreckingball.wordlier.repositories

import android.text.format.DateUtils
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.utils.DataStoreWrapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameResultsRepo(private val dataStore: DataStoreWrapper) {
    suspend fun updateGameResults(won: Boolean, curRound: Int) {
        updateStreak()
        updateWin(won, curRound)
    }
    private suspend fun updateStreak() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val todayString = sdf.format(Date())
        val lastDatePlayedString = dataStore.getLastDatePlayed("")

        val streak = dataStore.getStreak(0)
        if (lastDatePlayedString.isNotEmpty()) {
            val date = sdf.parse(lastDatePlayedString)
            if (date != null) {
                if (DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)) {
                    //last date played was yesterday, streak continues
                    val continuedStreak = streak + 1
                    updateStreakData(todayString, continuedStreak)

                    //update max streak
                    val maxStreak = dataStore.getMaxStreak(0)
                    if (continuedStreak > maxStreak) {
                        dataStore.putMaxStreak(maxStreak + 1)
                    }
                } else {
                    //streak is broken
                    updateStreakData(todayString, 1)
                }
            } else {
                //invalid date, treat as broken streak
                updateStreakData(todayString, 1)
            }
        } else {
            //could not find a date, it's probably the first game ever
            updateStreakData(todayString, 1)
            dataStore.putMaxStreak(1)
        }
    }

    private suspend fun updateStreakData(date: String, streak: Int) {
        dataStore.putLastDatePlayed(date)
        dataStore.putStreak(streak)

    }

    private suspend fun updateWin(won: Boolean, curRound: Int) {
        if (won) {
            //update last round won
            dataStore.putLastRoundWon(curRound)

            //update round wins
            val wins = dataStore.getRoundWins(curRound, 0)
            dataStore.putRoundWins(curRound, wins + 1)

            //update total wins
            val totalWins = dataStore.getTotalWins(0)
            dataStore.putTotalWins(totalWins + 1)
        } else {
            val totalLosses = dataStore.getTotalLosses(0)
            dataStore.putTotalLosses(totalLosses + 1)
        }
    }

    suspend fun getGameResults() : GameResults {
        val winsPerRound = dataStore.getAllRoundWins(0)
        val totalWins = dataStore.getTotalWins(0)
        val totalLosses = dataStore.getTotalLosses(0)
        val gamesPlayed = totalWins + totalLosses
        val winPercent = if (totalWins == 0) {
            0
        } else if (totalLosses == 0) {
            100
        } else {
            totalLosses / totalWins
        }
        val currentStreak = dataStore.getStreak(0)
        val maxStreak = dataStore.getMaxStreak(0)
        val lastRoundWon = dataStore.getLastRoundWon(0)
        return GameResults(
            winsPerRound = winsPerRound,
            gamesPlayed = gamesPlayed,
            winPercent = winPercent,
            currentStreak = currentStreak,
            maxStreak = maxStreak,
            lastRoundWon = lastRoundWon,
        )
    }

    suspend fun clearAll() {
        dataStore.clearAll()
    }
}