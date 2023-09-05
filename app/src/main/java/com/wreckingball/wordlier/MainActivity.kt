package com.wreckingball.wordlier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.theme.WordlierTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val playerRepo: PlayerRepo by inject()
    private val gameRepo: GameRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            val isLoggedIn = playerRepo.getPlayerName().isNotEmpty()
            val isPuzzleReady = gameRepo.isNewPuzzleReady()
            setContent {
                WordlierTheme {
                    WordlierApp(
                        isLoggedIn,
                        isPuzzleReady,
                    )
                }
            }
        }
    }
}

