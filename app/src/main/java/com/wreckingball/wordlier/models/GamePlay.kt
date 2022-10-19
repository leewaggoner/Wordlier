package com.wreckingball.wordlier.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6

class GamePlay(
    private val cursor: GameCursor,
) {
    val board: SnapshotStateList<SnapshotStateList<String>> = mutableStateListOf()

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
        if (key == "<-") {
            removeLetter()
        } else {
            addLetter(key)
        }
    }

    private fun addLetter(character: String) {
        reversed(Direction.FORWARD, board[cursor.getRow()][0].isEmpty()) {
            cursor.advance()
        }
        board[cursor.getRow()][cursor.getColumn()] = character
        cursor.advance()
    }

    private fun removeLetter() {
        reversed(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].isNotEmpty()) {
            cursor.back()
        }
        board[cursor.getRow()][cursor.getColumn()] = ""
        cursor.back()
    }

    private fun reversed(direction: Direction, state: Boolean, func: () -> Unit) {
        val reversed = cursor.reversed(direction)
        val backBefore = (reversed && !state)

        if (backBefore) {
            func()
        }
    }
}
