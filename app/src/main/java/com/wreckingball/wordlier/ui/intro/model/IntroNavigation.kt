package com.wreckingball.wordlier.ui.intro.model

sealed class IntroNavigation {
    data object GoToGame : IntroNavigation()
    data object Login : IntroNavigation()
    data object HowToPlay : IntroNavigation()
}