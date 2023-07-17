package com.wreckingball.wordlier.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.BACK
import com.wreckingball.wordlier.domain.ENTER
import com.wreckingball.wordlier.domain.GamePlay
import com.wreckingball.wordlier.domain.GameResult
import com.wreckingball.wordlier.domain.GameState
import com.wreckingball.wordlier.domain.GameplayState
import com.wreckingball.wordlier.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))

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
        when (key) {
            ENTER -> {
                state = state.copy(loading = true)
                viewModelScope.launch(Dispatchers.Main) {
                    when (gamePlay.handleEnter()) {
                        GameResult.NEXT_GUESS -> state = state.copy(loading = false)
                        GameResult.WIN -> {
                            state = state.copy(loading = false)
                            handleWin()
                        }
                        GameResult.LOSS -> {
                            state = state.copy(loading = false)
                            handleLoss()
                        }
                        GameResult.DO_NOTHING -> {}
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
}