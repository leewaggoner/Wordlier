package com.wreckingball.wordlier.ui.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.play.model.PlayNavigation
import com.wreckingball.wordlier.utils.OneShotEvent
import com.wreckingball.wordlier.utils.toEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PlayViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    lateinit var userName: String
    init {
        viewModelScope.launch(Dispatchers.IO) {
            userName = playerRepo.getPlayerName()
        }
    }

    val navigation = MutableSharedFlow<OneShotEvent<PlayNavigation>?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    fun playGame() {
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(PlayNavigation.GoToGame.toEvent())
        }
    }
}