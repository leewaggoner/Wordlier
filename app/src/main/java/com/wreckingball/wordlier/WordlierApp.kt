package com.wreckingball.wordlier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.wordlier.ui.game.Game
import com.wreckingball.wordlier.ui.intro.Intro
import com.wreckingball.wordlier.ui.login.Login

@Composable
fun WordlierApp() {
//    val playerRepo: PlayerRepo by inject()
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    var startDestination = Destinations.Intro
//    if (autoLoggedIn(playerRepo)) {
//        startDestination = Destinations.Game
//    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Intro) {
            Intro(actions = actions)
        }
        composable(Destinations.HowToPlay) {
//            HowToPlay(actions = actions)
        }
        composable(Destinations.Login) {
            Login(actions = actions)
        }
        composable(Destinations.Game) {
            Game()
        }
    }
}

//fun autoLoggedIn(playerRepo: PlayerRepo) : Boolean = playerRepo.getPlayerName.isNotEmpty()
