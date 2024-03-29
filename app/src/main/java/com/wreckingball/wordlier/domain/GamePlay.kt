package com.wreckingball.wordlier.domain

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.ui.theme.NormalCell

const val MAX_WORD_LENGTH = 5
const val MAX_GUESSES = 6
const val ENTER = "ENTER"
const val BACK = "BACK"

class GamePlay(private val cursor: GameCursor, private val gameRepo: GameRepo, private val gameRules: GameRules) {
    val board: SnapshotStateList<SnapshotStateList<GameLetter>> = mutableStateListOf()
    private var invalidWordUICallback: (state: GameplayState) -> Unit = { }
    private var word = "TRUST"

    fun getCurrentRow() = cursor.getRow()

    fun getCurrentWord() = word

    fun registerInvalidWordUICallback(callback: (state: GameplayState) -> Unit) {
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

    private fun initRow() : SnapshotStateList<GameLetter> {
        val row = mutableStateListOf<GameLetter>()
        for (i in 0 until MAX_WORD_LENGTH) {
            row.add(GameLetter(' ', NormalCell))
        }
        return row
    }

    suspend fun handleEnter() : GameResult {
        val guess = board[cursor.getRow()].map { it.letter }.joinToString(separator = "").trim()
        if (guess.length == MAX_WORD_LENGTH) {
            return if (isValidWord(guess)) {
                val coloredWord = gameRules.colorLetters(word, guess)
                val result = gameRules.handleGuess(word, guess, cursor.getRow(), coloredWord)
                if (result is GameResult.NextGuess) {
                    cursor.nextRow()
                }
                result
            } else {
                invalidWordUICallback(GameplayState.NotAWord)
                GameResult.DoNothing
            }
        } else {
            invalidWordUICallback(GameplayState.ShortWordLength)
            return GameResult.DoNothing
        }
    }

    private suspend fun isValidWord(guess: String) : Boolean {
        //make sure the guessed word is a real word
        return gameRepo.validateWord(guess)
    }

    fun updateLetter(rowIndex: Int, letterIndex: Int, letter: GameLetter) {
        board[rowIndex][letterIndex] = letter
    }

    fun handleRemoveLetter() {
        //if direction reversed from forward to backward and the last character is empty, we
        //need to back the cursor one extra space
        cursor.didReverse(Direction.BACKWARD, board[cursor.getRow()][MAX_WORD_LENGTH - 1].letter == ' ')

        board[cursor.getRow()][cursor.getColumn()] = GameLetter(' ', NormalCell)
        cursor.back()
    }

    fun handleAddLetter(character: String) {
        if (!cursor.atEnd) {
            //if direction reversed from backward to forward and the first character is not empty,
            //we need to advance the cursor one extra space
            cursor.didReverse(Direction.FORWARD, board[cursor.getRow()][0].letter != ' ')

            board[cursor.getRow()][cursor.getColumn()] = GameLetter(
                letter = character.first(),
                color = NormalCell
            )
            cursor.advance()
        }
    }
}
