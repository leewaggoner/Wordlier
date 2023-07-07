package com.wreckingball.wordlier.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.ui.theme.NormalCell

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(private val cursor: GameCursor, private val gameRepo: GameRepo, private val gameRules: GameRules) {
    val board: SnapshotStateList<SnapshotStateList<Pair<Char, Color>>> = mutableStateListOf()
    private var invalidWordUICallback: (msgId: Int) -> Unit = { }
    private var word = "TRUST"

    fun registerInvalidWordUICallback(callback: (msgId: Int) -> Unit) {
        invalidWordUICallback = callback
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

    suspend fun handleEnter() : GameResult {
        val guess = board[cursor.getRow()].map { it.first }.joinToString(separator = "").trim()
        if (guess.length == MAX_WORD_LENGTH) {
            return if (isValidWord(guess)) {
                colorLetters(word, guess)
                val result = gameRules.handleGuess(word, guess, cursor.getRow())
                if (result == GameResult.NEXT_GUESS) {
                    cursor.nextRow()
                }
                result
            } else {
                invalidWordUICallback(R.string.invalidWord)
                GameResult.DO_NOTHING
            }
        } else {
            invalidWordUICallback(R.string.invalidLength)
            return GameResult.DO_NOTHING
        }
    }

    private suspend fun isValidWord(guess: String) : Boolean {
        //make sure the guessed word is a real word
        return gameRepo.validateWord(guess)
    }

    private fun colorLetters(word: String, guess: String) {
        val charList = gameRules.colorLetters(word, guess)
        board[cursor.getRow()] = charList.toMutableStateList()
    }

    fun handleRemoveLetter() {
        //if direction reversed from forward to backward and the last character is empty, we
        //need to back the cursor one extra space
        cursor.didReverse(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].first == ' ')

        board[cursor.getRow()][cursor.getColumn()] = Pair(' ', NormalCell)
        cursor.back()
    }

    fun handleAddLetter(character: String) {
        if (!cursor.atEnd) {
            //if direction reversed from backward to forward and the first character is not empty,
            //we need to advance the cursor one extra space
            cursor.didReverse(Direction.FORWARD, board[cursor.getRow()][0].first != ' ')

            board[cursor.getRow()][cursor.getColumn()] = Pair(character.first(), NormalCell)
            cursor.advance()
        }
    }
}
