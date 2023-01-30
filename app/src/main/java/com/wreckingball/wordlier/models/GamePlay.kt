package com.wreckingball.wordlier.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.wreckingball.wordlier.repositories.GameRepo

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(private val cursor: GameCursor, private val gameRepo: GameRepo) {
    val board: SnapshotStateList<SnapshotStateList<String>> = mutableStateListOf()
    private var checkingInvalidWordCallback: () -> Unit = { }
    private var invalidWordUICallback: () -> Unit = { }
    private var gameResultUICallback: (GameResult) -> Unit = { }
    private var guessResultUICallback: (List<Pair<String, GuessResult>>) -> Unit = { }
    private var word = "TRUST"

    fun setupCheckingInvalidWordCallback(callback: () -> Unit) {
        checkingInvalidWordCallback = callback
    }

    fun setupGameResultUICallback(callback: (GameResult) -> Unit) {
        gameResultUICallback = callback
    }

    fun setupInvalidWordUICallback(callback: () -> Unit) {
        invalidWordUICallback = callback
    }

    fun setupGuessResultUICallback(callback: (List<Pair<String, GuessResult>>) -> Unit) {
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

    private fun initRow() : SnapshotStateList<String> {
        val row = mutableStateListOf<String>()
        for (i in 0 until MAX_WORD_LENGTH) {
            row.add("")
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

    private suspend fun handleGuess() {
        val guess = board[cursor.getRow()].joinToString(separator = "")

        if (guess.length == MAX_WORD_LENGTH) {
            //make sure the guessed word is a real word
            checkingInvalidWordCallback()
            if (!isValidWord(guess)) {
                invalidWordUICallback()
                return
            }

            if (guess != word) {
                //guess is incorrect
                if (cursor.getRow() < MAX_GUESSES - 1) {
                    colorLetters(guess)
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
        val letterMap = mutableListOf<Pair<String, GuessResult>>()
        for (index in word.indices) {
            var letterColor = Pair(first = guess[index].toString(), GuessResult.WRONG_LETTER)
            if (word.contains(guess[index].toString())) {
                letterColor = letterColor.copy(second = GuessResult.WRONG_POSITION)
            }
            if (guess[index] == word[index]) {
                letterColor = letterColor.copy(second = GuessResult.CORRECT)
            }
            letterMap.add(letterColor)
        }
        guessResultUICallback(letterMap)
    }

    companion object {
        enum class GuessResult {
            CORRECT,
            WRONG_LETTER,
            WRONG_POSITION,
        }
        enum class GameResult {
            WIN,
            LOSS
        }
    }
}
