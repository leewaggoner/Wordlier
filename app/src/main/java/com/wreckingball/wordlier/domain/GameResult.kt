package com.wreckingball.wordlier.domain

sealed interface GameResult {
    data object DoNothing : GameResult
    class NextGuess(val coloredWord: List<GameLetter>) : GameResult
    class Win(val coloredWord: List<GameLetter>) : GameResult
    class Loss(val coloredWord: List<GameLetter>) : GameResult
}