package com.wreckingball.wordlier.domain

import androidx.compose.runtime.snapshots.SnapshotStateList

data class GameState(
    val board: SnapshotStateList<SnapshotStateList<GameLetter>>,
    val state: GameplayState = GameplayState.GameStart,
    val loading: Boolean = false,
    val errMsgId: Int = 0,
    val shakeRow: Int = -1,
    val flipRow: Int = -1,
    val flipIndex: Int = -1,
)