package com.wreckingball.wordlier.domain

import androidx.compose.ui.graphics.Color

sealed class GameResult() {
    object DoNothing : GameResult()
    class NextGuess(val coloredWord: List<Pair<Char, Color>>) : GameResult()
    class Win(val coloredWord: List<Pair<Char, Color>>) : GameResult()
    class Loss(val coloredWord: List<Pair<Char, Color>>) : GameResult()
}