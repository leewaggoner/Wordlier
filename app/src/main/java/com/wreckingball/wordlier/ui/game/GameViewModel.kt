package com.wreckingball.wordlier.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.models.BaseViewModel
import com.wreckingball.wordlier.models.GamePlay
import com.wreckingball.wordlier.models.GameResult
import com.wreckingball.wordlier.models.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))

    init {
        gamePlay.initializeGame()
        gamePlay.registerGameResultUICallback { result ->
            state = state.copy(loading = false)
            when (result) {
                GameResult.WIN -> handleWin()
                GameResult.LOSS -> handleLoss()
                else -> {}
            }
        }
        gamePlay.registerCheckingInvalidWordCallback {
            state = state.copy(loading = true)
        }
        gamePlay.registerInvalidWordUICallback { msgId ->
            state = state.copy(loading = false)
            handleInvalidWord(msgId)
        }
        gamePlay.registerGuessResultUICallback {
            state = state.copy(loading = false)
        }
    }

    private fun handleWin() {
        Log.e("-----LEE-----", "You won!")
    }

    private fun handleLoss() {
        Log.e("-----LEE-----", "You lost!")
    }

    private fun handleInvalidWord(msgId: Int) {
        //launch error snack bar
        Log.e("-----LEE-----", "Not a word. Try again!")
    }

    fun onKeyboardClick(key: String) {
        viewModelScope.launch(Dispatchers.Main) {
            gamePlay.handleInput(key)
            state = state.copy(board = gamePlay.board)
        }
    }
}