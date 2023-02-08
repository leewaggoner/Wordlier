package com.wreckingball.wordlier.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(private val cursor: GameCursor, private val gameRepo: GameRepo) {
    val board: SnapshotStateList<SnapshotStateList<Pair<Char, Color>>> = mutableStateListOf()
    private var checkingInvalidWordCallback: () -> Unit = { }
    private var invalidWordUICallback: () -> Unit = { }
    private var gameResultUICallback: (GameResult) -> Unit = { }
    private var guessResultUICallback: () -> Unit = { }
    private var word = "TRUST"

    fun registerCheckingInvalidWordCallback(callback: () -> Unit) {
        checkingInvalidWordCallback = callback
    }

    fun registerGameResultUICallback(callback: (GameResult) -> Unit) {
        gameResultUICallback = callback
    }

    fun registerInvalidWordUICallback(callback: () -> Unit) {
        invalidWordUICallback = callback
    }

    fun registerGuessResultUICallback(callback: () -> Unit) {
        guessResultUICallback = callback
    }

    fun initializeGame() {
        //initialize board
        for (i in 0 until MAX_GUESSES) {
            board.add(initRow())
        }

        //init game repo
        gameRepo.initWords()

        //get daily word
        word = gameRepo.getDailyWord()
    }

    private fun initRow() : SnapshotStateList<Pair<Char, Color>> {
        val row = mutableStateListOf<Pair<Char, Color>>()
        for (i in 0 until MAX_WORD_LENGTH) {
            row.add(Pair(' ', NormalCell))
        }
        return row
    }

    suspend fun handleInput(key: String) {
        when (key) {
            ENTER -> handleGuess()
            BACK -> removeLetter()
            else -> addLetter(key)
        }
    }

    private fun addLetter(character: String) {
        if (!cursor.atEnd) {
            //if direction reversed from backward to forward and the first character is not empty,
            //we need to advance the cursor one extra space
            cursor.didReverse(Direction.FORWARD, board[cursor.getRow()][0].first != ' ')

            board[cursor.getRow()][cursor.getColumn()] = Pair(character.first(), NormalCell)
            cursor.advance()
        }
    }

    private fun removeLetter() {
        //if direction reversed from forward to backward and the last character is empty, we
        //need to back the cursor one extra space
        cursor.didReverse(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].first == ' ')

        board[cursor.getRow()][cursor.getColumn()] = Pair(' ', NormalCell)
        cursor.back()
    }

    private suspend fun handleGuess() {
        val guess = board[cursor.getRow()].map { it.first }.joinToString(separator = "")

        if (guess.length == MAX_WORD_LENGTH) {
            //make sure the guessed word is a real word
            checkingInvalidWordCallback()
            if (!isValidWord(guess)) {
                invalidWordUICallback()
                return
            }

            colorLetters(guess)
            if (guess != word) {
                //guess is incorrect
                if (cursor.getRow() < MAX_GUESSES - 1) {
                    cursor.nextRow()
                } else {
                    //last guess, game is over
                    gameResultUICallback(GameResult.LOSS)
                }
            } else {
                //guess is correct!
                gameResultUICallback(GameResult.WIN)
            }
        }
    }

    private suspend fun isValidWord(word: String) : Boolean {
        return gameRepo.validateWord(word)
    }

    private fun colorLetters(guess: String) {
        val guessList = guess.toList()
        val resultList = mutableListOf<Color>()
        for ((index, letter) in guessList.withIndex()) {
            resultList.add(if (letter == word[index]) {
                CorrectLetterCell
            } else {
                WrongLetterCell
            })
        }
        val result = guessList.zip(resultList)
        board[cursor.getRow()] = result.toMutableStateList()
        guessResultUICallback()
    }

    companion object {
        enum class GameResult {
            WIN,
            LOSS
        }
    }
}
