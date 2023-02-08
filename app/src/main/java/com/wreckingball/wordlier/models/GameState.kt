package com.wreckingball.wordlier.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

data class GameState(
    val board: SnapshotStateList<SnapshotStateList<Pair<Char, Color>>>,
    val state: GameplayState = GameplayState.START_ROUND,
    val loading: Boolean = false,
)