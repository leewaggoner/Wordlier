package com.wreckingball.wordlier.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.models.BACK
import com.wreckingball.wordlier.models.BaseViewModel
import com.wreckingball.wordlier.models.ENTER
import com.wreckingball.wordlier.models.GamePlay
import com.wreckingball.wordlier.models.GameResult
import com.wreckingball.wordlier.models.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))

    init {
        gamePlay.initializeGame()
        gamePlay.registerInvalidWordUICallback { msgId ->
            handleInvalidWord(msgId)
        }
    }

    private fun handleWin() {
        Log.e("-----LEE-----", "You won!")
    }

    private fun handleLoss() {
        Log.e("-----LEE-----", "You lost!")
    }

    private fun handleInvalidWord(msgId: Int) {
        state = state.copy(loading = false)
        //launch error snack bar
        Log.e("-----LEE-----", "Not a word. Try again!")
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
                        else -> {}
                    }
                }
            }
            BACK -> gamePlay.handleRemoveLetter()
            else -> gamePlay.handleAddLetter(key)
        }
        state = state.copy(board = gamePlay.board)
    }
}