package com.wreckingball.wordlier

import androidx.navigation.NavController
import com.wreckingball.wordlier.ui.home.Home

object Destinations {
    const val Login = "login"
    const val Home = "home"
}

class Actions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(Destinations.Home)
    }
}