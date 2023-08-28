package com.wreckingball.wordlier.ui.login.model

sealed class LoginNavigation {
    data object GoToGame : LoginNavigation()
}