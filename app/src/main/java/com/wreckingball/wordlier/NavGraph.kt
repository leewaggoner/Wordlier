package com.wreckingball.wordlier

import androidx.navigation.NavController

object Destinations {
    const val Login = "login"
    const val Game = "game"
}

class Actions(navController: NavController) {
    val navigateToGame: () -> Unit = {
        navController.navigate(Destinations.Game) {
            popUpTo(Destinations.Login) {
                inclusive = true
            }
        }
    }
}