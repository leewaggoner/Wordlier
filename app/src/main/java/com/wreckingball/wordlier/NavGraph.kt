package com.wreckingball.wordlier

import androidx.navigation.NavController

object Destinations {
    const val Intro = "intro"
    const val HowToPlay = "howToPlay"
    const val Login = "login"
    const val Game = "game"
}

class Actions(navController: NavController) {
    val navigateToLogin: () -> Unit = {
        navController.navigate(Destinations.Login)
    }
    val navigateToHowToPlay: () -> Unit = {
        navController.navigate(Destinations.HowToPlay)
    }
    val navigateToGame: () -> Unit = {
        navController.navigate(Destinations.Game) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}