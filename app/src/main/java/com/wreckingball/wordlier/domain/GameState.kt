package com.wreckingball.wordlier.domain

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.snapshots.SnapshotStateList

data class GameState(
    val board: SnapshotStateList<SnapshotStateList<GameLetter>>,
    val state: GameplayState = GameplayState.GameStart,
    val loading: Boolean = false,
    val msgId: Int = 0,
    val msg: String = "",
    val msgDuration: SnackbarDuration = SnackbarDuration.Short,
    val shakeRow: Int = -1,
    val waveRow: Int = -1,
    val waveIndex: Int = -1,
    val flipRow: Int = -1,
    val flipIndex: Int = -1,
    val usedLetters: List<GameLetter> = listOf(),
    val showResults: Boolean = false,
    val resultsUpdated: Boolean = false,
    val gameResults: GameResults? = null,
)