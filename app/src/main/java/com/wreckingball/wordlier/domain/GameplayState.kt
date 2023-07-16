package com.wreckingball.wordlier.domain

sealed class GameplayState {
    object GameStart : GameplayState()
    object NotAWord : GameplayState()
    object ShortWordLength : GameplayState()

}