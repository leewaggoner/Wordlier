package com.wreckingball.wordlier.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.BACK
import com.wreckingball.wordlier.domain.ENTER
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.domain.GamePlay
import com.wreckingball.wordlier.domain.GameResult
import com.wreckingball.wordlier.domain.GameState
import com.wreckingball.wordlier.domain.GameplayState
import com.wreckingball.wordlier.domain.MAX_WORD_LENGTH
import com.wreckingball.wordlier.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))
    private var gameResult: GameResult = GameResult.DoNothing
    private var curGuess: List<GameLetter>? = null

    init {
        gamePlay.initializeGame()
        gamePlay.registerInvalidWordUICallback { state ->
            handleInvalidWord(state)
        }
    }

    private fun handleWin() {
        Log.e("-----LEE-----", "You won!")
    }

    private fun handleLoss() {
        Log.e("-----LEE-----", "You lost!")
    }

    private fun handleInvalidWord(gameplayState: GameplayState) {
        //launch error snack bar
        val currentRow = gamePlay.getCurrentRow()
        when (gameplayState) {
            GameplayState.NotAWord -> {
                state = state.copy(
                    loading = false,
                    errMsgId = R.string.invalidWord,
                    shakeRow = currentRow
                )
            }
            GameplayState.ShortWordLength -> {
                state = state.copy(
                    loading = false,
                    errMsgId = R.string.invalidLength,
                    shakeRow = currentRow
                )
            }
            else -> {}
        }
    }

    fun clearErrorMsg() {
        state = state.copy(errMsgId = 0)
    }

    fun onShakeFinished() {
        state = state.copy(shakeRow = -1)
    }

    fun onKeyboardClick(key: String) {
        if (gameResult is GameResult.Win || gameResult is GameResult.Loss) {
            //game is over -- no more input, please
            return
        }
        when (key) {
            ENTER -> {
                state = state.copy(loading = true)
                viewModelScope.launch(Dispatchers.Main) {
                    gameResult = gamePlay.handleEnter()
                    when (val result = gameResult) {
                        is GameResult.NextGuess -> {
                            state = state.copy(loading = false)
                            //at this point the row has already been advanced
                            startLetterFlip(gamePlay.getCurrentRow() - 1, result.coloredWord)
                        }
                        is GameResult.Win -> {
                            state = state.copy(loading = false)
                            startLetterFlip(gamePlay.getCurrentRow(), result.coloredWord)
                            handleWin()
                        }
                        is GameResult.Loss -> {
                            state = state.copy(loading = false)
                            startLetterFlip(gamePlay.getCurrentRow(), result.coloredWord)
                            handleLoss()
                        }
                        GameResult.DoNothing -> {}
                    }
                }
            }
            BACK -> {
                gamePlay.handleRemoveLetter()
                state = state.copy(board = gamePlay.board)
            }
            else -> {
                gamePlay.handleAddLetter(key)
                state = state.copy(board = gamePlay.board)
            }
        }
    }

    private fun startLetterFlip(curRow: Int, coloredWord: List<GameLetter>?) {
        curGuess = coloredWord
        curGuess?.let { word ->
            gamePlay.updateLetter(curRow, 0, word[0])
            state = state.copy(board = gamePlay.board, flipRow = curRow, flipIndex = 0)
        }
    }

    fun onFlipFinished() {
        curGuess?.let { word ->
            val flipIndex = state.flipIndex + 1
            val curRow = state.flipRow
            state = if (flipIndex < MAX_WORD_LENGTH) {
                gamePlay.updateLetter(curRow, flipIndex, word[flipIndex])
                state.copy(board = gamePlay.board, flipRow = curRow, flipIndex = flipIndex)
            } else {
                curGuess = null
                state.copy(flipRow = -1, flipIndex = -1)
            }
        }
    }
}