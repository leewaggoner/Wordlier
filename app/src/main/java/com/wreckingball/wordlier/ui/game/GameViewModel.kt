package com.wreckingball.wordlier.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wreckingball.wordlier.models.BaseViewModel
import com.wreckingball.wordlier.models.GamePlay
import com.wreckingball.wordlier.models.GameState

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))

    init {
        gamePlay.initializeGame()
        gamePlay.setupResultUICallback { result ->
            when (result) {
                GamePlay.Companion.GameResult.WIN -> handleWin()
                GamePlay.Companion.GameResult.LOSS -> handleLoss()
            }
        }
    }

    private fun handleWin() {
        Log.e("-----LEE-----", "You won!")
    }

    private fun handleLoss() {
        Log.e("-----LEE-----", "You lost!")
    }

    fun onKeyboardClick(key: String) {
        gamePlay.handleInput(key)
        state = state.copy(board = gamePlay.board)
    }
}