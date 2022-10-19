package com.wreckingball.wordlier.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wreckingball.wordlier.models.BaseViewModel
import com.wreckingball.wordlier.models.GamePlay
import com.wreckingball.wordlier.models.GameState

class GameViewModel(
    private val gamePlay: GamePlay,
    ) : BaseViewModel() {

    var state by mutableStateOf(GameState(gamePlay.board))

    fun onKeyboardClick(key: String) {
        gamePlay.handleInput(key)
        state = state.copy(board = gamePlay.board)
    }
}