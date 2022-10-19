package com.wreckingball.wordlier.models

import androidx.compose.runtime.snapshots.SnapshotStateList

data class GameState(
    val board: SnapshotStateList<SnapshotStateList<String>>,
    val state: GameplayState = GameplayState.START_ROUND,
)