package com.wreckingball.wordlier.domain

data class GameResults(
    val guesses: List<Int>,
    val maxGuesses: Int = guesses.max(),
    val gamesPlayed: Int,
    val winPercent: Int,
    val currentStreak: Int,
    val maxStreak: Int,
    val lastRoundWon: Int,
)