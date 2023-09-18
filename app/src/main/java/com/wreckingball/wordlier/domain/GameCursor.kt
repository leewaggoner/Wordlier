package com.wreckingball.wordlier.domain

data class Cursor(val row: Int, val column: Int)

enum class Direction {
    FORWARD,
    BACKWARD,
}

class GameCursor {
    private var cursor = Cursor(0, 0)
    private var currectDirection = Direction.FORWARD

    var atEnd = false
    fun getRow() = cursor.row
    fun getColumn() = cursor.column

    fun reset() {
        cursor = Cursor(0, 0)
    }

    fun advance() {
        if (cursor.row < MAX_GUESSES) {
            if (cursor.column < MAX_WORD_LENGTH - 1) {
                cursor = Cursor(cursor.row, cursor.column + 1)
            } else {
                atEnd = true
            }
        }
    }

    fun back() {
        atEnd = false
        if (cursor.row >= 0) {
            if (cursor.column > 0) {
                cursor = Cursor(cursor.row, cursor.column - 1)
            }
        }
    }

    /**
     * If the direction is reversed from backward to forward and the first character is not empty,
     * we need to advance the cursor one extra space.
     * If the direction is reversed from forward to backward and the last character is empty, we
     * need to back the cursor one extra space
     */
    fun didReverse(direction: Direction, advanceCursor: Boolean) =
        if (direction != currectDirection) {
            if (advanceCursor) {
                //reversed cursor direction, so we need to advance/back the cursor
                when (direction) {
                    Direction.FORWARD -> { advance() }
                    Direction.BACKWARD ->{ back() }
                }
            }
            currectDirection = direction
            true
        } else {
            false
        }

    fun nextRow() {
        atEnd = false
        cursor = Cursor(cursor.row + 1, 0)
    }
}