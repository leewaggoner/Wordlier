package com.wreckingball.wordlier.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(
    private val cursor: GameCursor,
) {
    val board: SnapshotStateList<SnapshotStateList<String>> = mutableStateListOf()
    private val word = "TRUST"

    init {
        for (i in 0 until MAX_GUESSES) {
            board.add(getRow())
        }
    }

    private fun getRow(): SnapshotStateList<String> {
        val row = mutableStateListOf<String>()
        for (i in 0 until MAX_WORD_LENGTH) {
            row.add("")
        }
        return row
    }

    fun handleInput(key: String) {
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
            cursor.didReverse(Direction.FORWARD, board[cursor.getRow()][0].isNotEmpty())

            board[cursor.getRow()][cursor.getColumn()] = character
            cursor.advance()
        }
    }

    private fun removeLetter() {
        //if direction reversed from forward to backward and the last character is empty, we
        //need to back the cursor one extra space
        cursor.didReverse(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].isEmpty())

        board[cursor.getRow()][cursor.getColumn()] = ""
        cursor.back()
    }

    private fun handleGuess() {
        val guess = board[cursor.getRow()].joinToString(separator = "")
        if (guess.length == MAX_WORD_LENGTH) {
            if (guess != word) {
                if (cursor.getRow() < MAX_GUESSES - 1) {
                    cursor.nextRow()
                } else {
                    Log.e("--LEE--", "You lost!")
                }
            }
        }
    }
}
