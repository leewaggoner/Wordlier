package com.wreckingball.wordlier.models

class Board {
    var guesses: List<List<String>>

    init {
        guesses = listOf(
            listOf("", "", "", "", ""),
            listOf("", "", "", "", ""),
            listOf("", "", "", "", ""),
            listOf("", "", "", "", ""),
            listOf("", "", "", "", ""),
            listOf("", "", "", "", ""),
        )
    }
}