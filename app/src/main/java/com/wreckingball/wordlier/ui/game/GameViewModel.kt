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
        gamePlay.setupGameResultUICallback { result ->
            when (result) {
                GamePlay.Companion.GameResult.WIN -> handleWin()
                GamePlay.Companion.GameResult.LOSS -> handleLoss()
            }
        }
        gamePlay.setupInvalidWordUICallback {
            handleInvalidWord()
        }
        gamePlay.setupGuessResultUICallback { guess ->
            handleGuessResult(guess)
        }
    }

    private fun handleWin() {
        Log.e("-----LEE-----", "You won!")
    }

    private fun handleLoss() {
        Log.e("-----LEE-----", "You lost!")
    }

    private fun handleInvalidWord() {

    }

    private fun handleGuessResult(guess: List<Pair<String, GamePlay.Companion.GuessResult>>) {

    }

    fun onKeyboardClick(key: String) {
        gamePlay.handleInput(key)
        state = state.copy(board = gamePlay.board)
    }
}