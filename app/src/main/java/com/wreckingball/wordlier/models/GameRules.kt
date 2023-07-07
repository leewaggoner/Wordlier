package com.wreckingball.wordlier.models

import androidx.compose.ui.graphics.Color
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

class GameRules {
    fun colorLetters(theWord: String, guess: String) : List<Pair<Char, Color>> {
        val guessList = guess.toList()
        val resultList = mutableListOf<Color>()
        for ((index, letter) in guessList.withIndex()) {
            resultList.add(
                if (theWord.contains(letter)) {
                    if (letter == theWord[index]) {
                        CorrectLetterCell
                    } else {
                        WrongPositionCell
                    }
                } else {
                    WrongLetterCell
                }
            )
        }
        return guessList.zip(resultList)
    }

    fun handleGuess(word: String, guess: String, currentRow: Int) : GameResult {
        return if (guess != word) {
            //guess is incorrect
            if (currentRow < MAX_GUESSES - 1) {
                GameResult.NEXT_GUESS
            } else {
                //last guess, game is over
                GameResult.LOSS
            }
        } else {
            //guess is correct!
            GameResult.WIN
        }
    }
}