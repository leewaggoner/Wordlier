package com.wreckingball.wordlier.ui.login

sealed class LoginNavigation {
    object GoToHome : LoginNavigation()
}