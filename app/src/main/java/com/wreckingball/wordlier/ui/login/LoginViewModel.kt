package com.wreckingball.wordlier.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wreckingball.wordlier.models.PlayerData
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.login.model.LoginState
import com.wreckingball.wordlier.utils.OneShotEvent
import com.wreckingball.wordlier.utils.toEvent
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    val navigation = MutableStateFlow<OneShotEvent<LoginNavigation>?>(null)
    var state by mutableStateOf(LoginState())

    init {
        val name = playerRepo.getPlayerData().name
        state = state.copy(name = name, buttonEnabled = name.isNotEmpty())
    }

    fun verifyLogin() {
        if (state.name.isNotEmpty()) {
            navigation.value = LoginNavigation.GoToHome.toEvent()
        }
    }

    fun onNameChange(newName: String) {
        state = state.copy(name = newName, buttonEnabled = newName.isNotEmpty())
    }

    fun setPlayerData(){
        playerRepo.setPlayerData(PlayerData(state.name))
        navigation.value = LoginNavigation.GoToHome.toEvent()
    }
}