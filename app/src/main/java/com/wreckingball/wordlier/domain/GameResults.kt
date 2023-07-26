package com.wreckingball.wordlier.domain

data class GameResults(
    val winsPerRound: List<Int>,
    val maxWins: Int = winsPerRound.max(),
    val gamesPlayed: Int,
    val winPercent: Int,
    val currentStreak: Int,
    val maxStreak: Int,
    val lastRoundWon: Int,
)