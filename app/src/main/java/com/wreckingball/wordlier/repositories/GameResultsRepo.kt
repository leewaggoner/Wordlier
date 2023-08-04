package com.wreckingball.wordlier.repositories

import android.text.format.DateUtils
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.utils.DataStoreWrapper
import com.wreckingball.wordlier.utils.isYesterday
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameResultsRepo(private val dataStore: DataStoreWrapper) {
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    suspend fun updateGameResults(won: Boolean, curRound: Int) {
        when (dateStreakIsBroken()) {
            BrokenStreak.UNBROKEN -> handleUnbrokenStreak(won)
            BrokenStreak.BROKEN -> handleBrokenStreak(won)
            BrokenStreak.FIRST_GAME -> handleFirstGame(won)
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

    private suspend fun dateStreakIsBroken() : BrokenStreak {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val lastDatePlayedString = dataStore.getLastDatePlayed("")

        return if (lastDatePlayedString.isNotEmpty()) {
            val lastDatePlayed = sdf.parse(lastDatePlayedString)
            if (lastDatePlayed != null) {
                // if the date last played is today, do nothing
                if (!DateUtils.isToday(lastDatePlayed.time)) {
                    if (lastDatePlayed.isYesterday()) {
                        // last game played was yesterday, the streak continues!
                        BrokenStreak.UNBROKEN
                    } else {
                        // last game played was NOT yesterday, the streak is broken
                        BrokenStreak.BROKEN
                    }
                } else {
                    // last game played was today -- return not broken for testing purposes
                    BrokenStreak.UNBROKEN
                }
            } else {
                // invalid date
                BrokenStreak.BROKEN
            }
        } else {
            // empty date string, probably the first game ever played
            BrokenStreak.FIRST_GAME
        }
    }

    private fun dateIsYesterday(date: Date) : Boolean {
        return DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)
    }

    private suspend fun updateStreakData(streak: Int) {
        val currentDateString = sdf.format(Date())
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

    companion object {
        enum class BrokenStreak {
            BROKEN,
            UNBROKEN,
            FIRST_GAME
        }
    }
}