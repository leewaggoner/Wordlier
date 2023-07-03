package com.wreckingball.wordlier.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(private val cursor: GameCursor, private val gameRepo: GameRepo) {
    val board: SnapshotStateList<SnapshotStateList<Pair<Char, Color>>> = mutableStateListOf()
    private var checkingInvalidWordCallback: () -> Unit = { }
    private var invalidWordUICallback: (msgId: Int) -> Unit = { }
    private var gameResultUICallback: (GameResult) -> Unit = { }
    private var guessResultUICallback: () -> Unit = { }
    private var word = "TRUST"

    fun registerCheckingInvalidWordCallback(callback: () -> Unit) {
        checkingInvalidWordCallback = callback
    }

    fun registerGameResultUICallback(callback: (GameResult) -> Unit) {
        gameResultUICallback = callback
    }

    fun registerInvalidWordUICallback(callback: (msgId: Int) -> Unit) {
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
            ENTER -> handleEnter()
            BACK -> handleRemoveLetter()
            else -> handleAddLetter(key)
        }
    }

    private suspend fun handleEnter() {
        val guess = board[cursor.getRow()].map { it.first }.joinToString(separator = "").trim()
        if (guess.length == MAX_WORD_LENGTH) {
            if (isValidWord(guess)) {
                gameResultUICallback(handleGuess(guess))
            } else {
                invalidWordUICallback(R.string.invalidWord)
            }
        } else {
            invalidWordUICallback(R.string.invalidLength)
        }
    }

    private suspend fun isValidWord(guess: String) : Boolean {
        //make sure the guessed word is a real word
        checkingInvalidWordCallback()
        return gameRepo.validateWord(guess)
    }

    private fun handleGuess(guess: String) : GameResult {
        val charList = colorLetters(word, guess)
        board[cursor.getRow()] = charList.toMutableStateList()
        guessResultUICallback()

        return if (guess != word) {
            //guess is incorrect
            if (cursor.getRow() < MAX_GUESSES - 1) {
                cursor.nextRow()
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

    private fun handleRemoveLetter() {
        //if direction reversed from forward to backward and the last character is empty, we
        //need to back the cursor one extra space
        cursor.didReverse(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].first == ' ')

        board[cursor.getRow()][cursor.getColumn()] = Pair(' ', NormalCell)
        cursor.back()
    }

    private fun handleAddLetter(character: String) {
        if (!cursor.atEnd) {
            //if direction reversed from backward to forward and the first character is not empty,
            //we need to advance the cursor one extra space
            cursor.didReverse(Direction.FORWARD, board[cursor.getRow()][0].first != ' ')

            board[cursor.getRow()][cursor.getColumn()] = Pair(character.first(), NormalCell)
            cursor.advance()
        }
    }

    companion object {
        enum class GameResult {
            NEXT_GUESS,
            WIN,
            LOSS
        }
    }
}
