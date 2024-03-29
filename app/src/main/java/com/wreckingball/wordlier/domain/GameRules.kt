package com.wreckingball.wordlier.domain

import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

class GameRules {
    fun colorLetters(word: String, guess: String): List<GameLetter> {
        val characterCount = initCharacterCount(word)
        val guessList = guess.toList()
        val resultList = mutableListOf(
            WrongLetterCell,
            WrongLetterCell,
            WrongLetterCell,
            WrongLetterCell,
            WrongLetterCell
        )

        //need to do two passes because word = adult and guess = trust shows the first 't' as
        //WrongPositionCell instead of WrongLetterCell and the last 't' as WrongLetterCell
        for ((index, letter) in guessList.withIndex()) {
            if (letter == word[index]) {
                resultList[index] = CorrectLetterCell
                //need to mark a subsequent hit as a wrong letter
                val count = (characterCount[letter] ?: 1) - 1
                characterCount[letter] = count
            }
        }
        for ((index, letter) in guessList.withIndex()) {
            if (word.contains(letter)
                && resultList[index] != CorrectLetterCell
                && (characterCount[letter] ?: 0) > 0) {
                resultList[index] = WrongPositionCell
                //need to mark a subsequent hit as a wrong letter
                val count = (characterCount[letter] ?: 1) - 1
                characterCount[letter] = count
            }
        }

        val letters = guessList.zip(resultList)

        return letters.map { letter -> GameLetter(letter = letter.first, color = letter.second) }
    }

    private fun initCharacterCount(word: String) : MutableMap<Char, Int> {
        val characterCount = mutableMapOf<Char, Int>()
        for (letter in word) {
            if (characterCount.contains(letter)) {
                val count = characterCount[letter]
                count?.let {
                    characterCount[letter] = it + 1
                }
            } else {
                characterCount[letter] = 1
            }
        }
        return characterCount
    }

    fun handleGuess(
        word: String,
        guess: String,
        currentRow: Int,
        coloredWord: List<GameLetter>) : GameResult {
        return if (guess != word) {
            //guess is incorrect
            if (currentRow < MAX_GUESSES - 1) {
                GameResult.NextGuess(coloredWord)
            } else {
                //last guess, game is over
                GameResult.Loss(coloredWord)
            }
        } else {
            //guess is correct!
            GameResult.Win(coloredWord)
        }
    }
}