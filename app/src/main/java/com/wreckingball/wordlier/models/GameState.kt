package com.wreckingball.wordlier.models

data class GameState(
    val state: GameplayState = GameplayState.START_ROUND
) {
    val board: Board = Board()
}