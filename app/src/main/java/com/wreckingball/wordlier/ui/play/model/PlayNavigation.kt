package com.wreckingball.wordlier.ui.play.model

sealed class PlayNavigation {
    data object GoToGame : PlayNavigation()
}
