package com.wreckingball.wordlier.ui.login

import androidx.lifecycle.ViewModel
import com.wreckingball.wordlier.repositories.PlayerRepo

class LoginViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    var goToGame = getPlayerName().isNotBlank()
    fun setPlayerName(name: String) = playerRepo.setPlayerName(name)
    private fun getPlayerName() = playerRepo.getPlayerName()
}