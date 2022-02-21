package com.wreckingball.wordlier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.wordlier.ui.home.Home
import com.wreckingball.wordlier.ui.login.Login
import com.wreckingball.wordlier.ui.theme.WordlierTheme

@Composable
fun WordlierApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    WordlierTheme {
        NavHost(navController = navController, startDestination = Destinations.Login) {
            composable(Destinations.Login) {
                Login(goHome = actions.navigateToHome)
            }
            composable(Destinations.Home) {
                Home()
            }
        }
    }
}