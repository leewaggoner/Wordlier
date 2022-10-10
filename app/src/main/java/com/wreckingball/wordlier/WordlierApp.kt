package com.wreckingball.wordlier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.wordlier.ui.home.Home
import com.wreckingball.wordlier.ui.home.HomeViewModel
import com.wreckingball.wordlier.ui.login.Login
import com.wreckingball.wordlier.ui.login.LoginViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun WordlierApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    val startDestination = Destinations.Login
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Login) {
            val viewModel: LoginViewModel by viewModel()
            Login(
                viewModel,
                actions,
            )
        }
        composable(Destinations.Home) {
            val viewModel: HomeViewModel by viewModel()
            Home(viewModel)
        }
    }
}