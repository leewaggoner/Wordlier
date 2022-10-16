package com.wreckingball.wordlier.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wreckingball.wordlier.models.GameState
import com.wreckingball.wordlier.models.GameplayState
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.game.model.GameNavigation
import com.wreckingball.wordlier.utils.OneShotEvent
import kotlinx.coroutines.flow.MutableStateFlow

class GameViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    val navigation = MutableStateFlow<OneShotEvent<GameNavigation>?>(null)
    val gameState by mutableStateOf(GameState())

    fun onGuessMade() {
        gameState.copy(state = GameplayState.GUESS_MADE)
    }
}