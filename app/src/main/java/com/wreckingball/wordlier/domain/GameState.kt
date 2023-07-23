package com.wreckingball.wordlier.domain

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

data class GameState(
    val board: SnapshotStateList<SnapshotStateList<Pair<Char, Color>>>,
    val state: GameplayState = GameplayState.GameStart,
    val loading: Boolean = false,
    val errMsgId: Int = 0,
    val shakeRow: Int = -1,
    val flipRow: Int = -1,
    val flipIndex: Int = -1,
)