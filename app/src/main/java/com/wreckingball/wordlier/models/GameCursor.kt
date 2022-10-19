package com.wreckingball.wordlier.models

enum class Direction {
    FORWARD,
    BACKWARD,
}

class GameCursor {
    private var cursor = Pair(0, 0)
    private var currectDirection = Direction.FORWARD

    fun getRow() = cursor.first
    fun getColumn() = cursor.second

    fun reset() {
        cursor = Pair(0, 0)
    }

    fun advance() {
        if (cursor.first < MAX_GUESSES) {
            if (cursor.second < MAX_WORD_LENGTH - 1) {
                cursor = Pair(cursor.first, cursor.second + 1)
            }
        }
    }

    fun back() {
        if (cursor.first >= 0) {
            if (cursor.second > 0) {
                cursor = Pair(cursor.first, cursor.second - 1)
            }
        }
    }

    fun reversed(direction: Direction) =
        if (direction != currectDirection) {
            currectDirection = direction
            true
        } else {
            false
        }
}