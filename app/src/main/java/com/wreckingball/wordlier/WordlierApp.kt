package com.wreckingball.wordlier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.game.Game
import com.wreckingball.wordlier.ui.game.GameViewModel
import com.wreckingball.wordlier.ui.login.Login
import com.wreckingball.wordlier.ui.login.LoginViewModel
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

@Composable
fun WordlierApp() {
    val playerRepo: PlayerRepo by inject()
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    var startDestination = Destinations.Login
    if (autoLoggedIn(playerRepo)) {
        startDestination = Destinations.Game
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Login) {
            Login(actions = actions)
        }
        composable(Destinations.Game) {
            Game()
        }
    }
}

fun autoLoggedIn(playerRepo: PlayerRepo) : Boolean = playerRepo.getPlayerData().name.isNotEmpty()
