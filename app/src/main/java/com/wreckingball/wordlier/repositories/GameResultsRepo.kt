package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.domain.StreakStatus
import com.wreckingball.wordlier.utils.DataStoreWrapper
import com.wreckingball.wordlier.utils.dateStreakResult
import com.wreckingball.wordlier.utils.wordlierDateToString
import java.util.Date

class GameResultsRepo(private val dataStore: DataStoreWrapper) {
    suspend fun updateGameResults(won: Boolean, curRound: Int) {
        val lastDatePlayedString = dataStore.getLastDatePlayed("")

        when (lastDatePlayedString.dateStreakResult()) {
            StreakStatus.UNBROKEN -> handleUnbrokenStreak(won)
            StreakStatus.BROKEN -> handleBrokenStreak(won)
            StreakStatus.FIRST_GAME -> handleFirstGame(won)
        }
        updateWin(won, curRound)
    }

    private suspend fun handleUnbrokenStreak(won: Boolean) {
        //the last date played was yesterday
        if (won) {
            //player won, streak continues
            val continuedStreak = dataStore.getStreak(0) + 1
            updateStreakData(continuedStreak)

            //update max streak
            val maxStreak = dataStore.getMaxStreak(0)
            if (continuedStreak > maxStreak) {
                dataStore.putMaxStreak(maxStreak + 1)
            }
        } else {
            //player lost, streak is broken
            updateStreakData(0)
        }
    }

    private suspend fun handleBrokenStreak(won: Boolean) {
        //the last date played was before yesterday
        if (won) {
            //player won, start a new streak
            updateStreakData(1)
        } else {
            //player lost, reset the streak
            updateStreakData(0)
        }
    }

    private suspend fun handleFirstGame(won: Boolean) {
        if (won) {
            //player won, start a new streak
            dataStore.putMaxStreak(1)
            updateStreakData(1)
        } else {
            //player lost, the streak hasn't started yet
            dataStore.putMaxStreak(0)
            updateStreakData(0)
        }
    }

    private suspend fun updateStreakData(streak: Int) {
        val currentDateString = Date().wordlierDateToString()
        dataStore.putLastDatePlayed(currentDateString)
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
            dataStore.putLastRoundWon(-1)
        }
    }

    suspend fun getGameResults() : GameResults {
        val winsPerRound = dataStore.getAllRoundWins(0)
        val totalWins = dataStore.getTotalWins(0)
        val totalLosses = dataStore.getTotalLosses(0)
        val gamesPlayed = totalWins + totalLosses
        val winPercentRaw = if (gamesPlayed == 0) {
            0f
        } else if (totalLosses == 0) {
            1f
        } else {
            totalWins.toFloat() / gamesPlayed.toFloat()
        }
        val winPercent = (winPercentRaw * 100).toInt()
        val currentStreak = dataStore.getStreak(0)
        val maxStreak = dataStore.getMaxStreak(0)
        val lastRoundWon = dataStore.getLastRoundWon(-1)
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