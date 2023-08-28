package com.wreckingball.wordlier.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.login.model.LoginNavigation
import com.wreckingball.wordlier.ui.login.model.LoginState
import com.wreckingball.wordlier.utils.OneShotEvent
import com.wreckingball.wordlier.utils.toEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    val navigation = MutableSharedFlow<OneShotEvent<LoginNavigation>?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    var state by mutableStateOf(LoginState())

    init {
        loadName()
    }

    private fun loadName() {
        viewModelScope.launch(Dispatchers.Main) {
            val name = playerRepo.getPlayerName()
            state = state.copy(name = name, buttonEnabled = name.isNotEmpty())
        }
    }

    fun onNameChange(newName: String) {
        state = state.copy(name = newName, buttonEnabled = newName.isNotEmpty())
    }

    fun setPlayerData(){
        viewModelScope.launch(Dispatchers.Main) {
            playerRepo.setPlayerName(PlayerData(state.name))
            navigation.emit(LoginNavigation.GoToGame.toEvent())
        }
    }
}