package com.wreckingball.wordlier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.wordlier.ui.game.Game
import com.wreckingball.wordlier.ui.howto.HowToPlay
import com.wreckingball.wordlier.ui.intro.Intro
import com.wreckingball.wordlier.ui.login.Login
import com.wreckingball.wordlier.ui.play.model.Play

@Composable
fun WordlierApp(
    isLoggedIn: Boolean,
    isPuzzleReady: Boolean,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    var startDestination = Destinations.Intro
    if (isLoggedIn) {
        startDestination = if (isPuzzleReady) {
            Destinations.Play
        } else {
            Destinations.Game
        }
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Intro) {
            Intro(actions = actions)
        }
        composable(Destinations.HowToPlay) {
            HowToPlay()
        }
        composable(Destinations.Login) {
            Login(actions = actions)
        }
        composable(Destinations.Play) {
            Play(actions = actions)
        }
        composable(Destinations.Game) {
            Game(isPuzzleReady = isPuzzleReady)
        }
    }
}

