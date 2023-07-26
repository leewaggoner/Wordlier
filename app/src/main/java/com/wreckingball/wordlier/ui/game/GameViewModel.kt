package com.wreckingball.wordlier.ui.game

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
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.domain.GameState
import com.wreckingball.wordlier.domain.GameplayState
import com.wreckingball.wordlier.domain.MAX_WORD_LENGTH
import com.wreckingball.wordlier.repositories.GameResultsRepo
import com.wreckingball.wordlier.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val gamePlay: GamePlay,
    private val gameResultsRepo: GameResultsRepo,
) : BaseViewModel() {
    var state by mutableStateOf(GameState(gamePlay.board))
    private var gameResult: GameResult = GameResult.DoNothing
    private var curGuess: List<GameLetter>? = null
    private var victoryMsg = listOf(
        R.string.amazing,
        R.string.incredible,
        R.string.fantastic,
        R.string.wellDone,
        R.string.great,
        R.string.whew,
    )
    var gameResults: GameResults? = null

    init {
        gamePlay.initializeGame()
        gamePlay.registerInvalidWordUICallback { state ->
            handleInvalidWord(state)
        }
        viewModelScope.launch(Dispatchers.Main) {
            gameResultsRepo.clearAll()
            getCurrentGameResults()
        }
    }

    private fun handleWin() {
        val curRow = gamePlay.getCurrentRow()
        updateGameResults()
        state = state.copy(waveRow = curRow, waveIndex = 0, msgId = victoryMsg[curRow])
    }

    private fun handleLoss() {
        val curRow = gamePlay.getCurrentRow()
        updateGameResults()
        state = state.copy(waveRow = curRow, waveIndex = 0, msgId = R.string.bummer)
    }

    private fun handleInvalidWord(gameplayState: GameplayState) {
        //launch error snack bar
        val currentRow = gamePlay.getCurrentRow()
        when (gameplayState) {
            GameplayState.NotAWord -> {
                state = state.copy(
                    loading = false,
                    msgId = R.string.invalidWord,
                    shakeRow = currentRow
                )
            }
            GameplayState.ShortWordLength -> {
                state = state.copy(
                    loading = false,
                    msgId = R.string.invalidLength,
                    shakeRow = currentRow
                )
            }
            else -> {}
        }
    }

    fun clearErrorMsg() {
        state = state.copy(msgId = 0)
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
                        }
                        is GameResult.Loss -> {
                            state = state.copy(loading = false)
                            startLetterFlip(gamePlay.getCurrentRow(), result.coloredWord)
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
            val letterList = state.usedLetters.toMutableList()
            letterList.addAll(word)
            gamePlay.updateLetter(curRow, 0, word[0])
            state = state.copy(
                board = gamePlay.board,
                flipRow = curRow,
                flipIndex = 0,
                usedLetters = letterList
            )
        }
    }

    fun onWaveFinished() {
        val waveIndex = state.waveIndex + 1
        val curRow = state.waveRow
        state = if (waveIndex < MAX_WORD_LENGTH) {
            state.copy(waveRow = curRow, waveIndex = waveIndex)
        } else {
            state.copy(waveRow = -1, waveIndex = -1, showResults = true)
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
                if (gameResult is GameResult.Win) {
                    //handle win
                    handleWin()
                } else if (gameResult is GameResult.Loss) {
                    handleLoss()
                }
                state.copy(flipRow = -1, flipIndex = -1)
            }
        }
    }

    private fun updateGameResults() {
        viewModelScope.launch(Dispatchers.Main) {
            saveGameResults()
            getCurrentGameResults()
        }
    }

    private suspend fun saveGameResults() {
        gameResultsRepo.updateGameResults(gameResult is GameResult.Win, gamePlay.getCurrentRow())
    }

    private suspend fun getCurrentGameResults() {
        gameResults = gameResultsRepo.getGameResults()
        state = state.copy(resultsUpdated = true)
    }
}