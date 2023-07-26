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
import com.wreckingball.wordlier.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val gamePlay: GamePlay) : BaseViewModel() {
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
    var gameResults: GameResults

    init {
        gamePlay.initializeGame()
        gamePlay.registerInvalidWordUICallback { state ->
            handleInvalidWord(state)
        }
        gameResults = GameResults(
            winsPerRound = listOf(0, 9, 36, 92, 97, 57),
            gamesPlayed = 316,
            winPercent = 92,
            currentStreak = 4,
            maxStreak = 36,
            lastRoundWon = 4,
        )
    }

    fun getCurrentRound() = gamePlay.getCurrentRow()

    private fun handleWin() {
        val curRow = gamePlay.getCurrentRow()
        saveGameResults()
        gameResults = getCurrentGameResults()
        state = state.copy(waveRow = curRow, waveIndex = 0, msgId = victoryMsg[curRow])
    }

    private fun handleLoss() {
        state = state.copy(msgId = R.string.bummer)
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

    private fun saveGameResults() {
        //save game results to the GameResultsRepo
    }

    private fun getCurrentGameResults() : GameResults {
        //get the current results from the GameResultsRepo
        val curRow = getCurrentRound()
        val winsPerRound = gameResults.winsPerRound.toMutableList()
        winsPerRound[curRow] = winsPerRound[curRow] + 1

        val gamesLost = 6
        val gamesWon = 316 + 1
        val winPercent = (gamesLost / gamesWon)
        val streakBroken = false
        return GameResults(
            winsPerRound = winsPerRound,
            lastRoundWon = curRow,
            gamesPlayed = gameResults.gamesPlayed + 1,
            winPercent = winPercent,
            currentStreak = if (streakBroken) 1 else gameResults.currentStreak + 1,
            maxStreak = if (gameResults.currentStreak > gameResults.maxStreak) {
                gameResults.currentStreak
            } else {
                gameResults.maxStreak
            },
        )
    }
}