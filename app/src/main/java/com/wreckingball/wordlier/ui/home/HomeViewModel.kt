package com.wreckingball.wordlier.ui.home

import androidx.lifecycle.ViewModel
import com.wreckingball.wordlier.repositories.PlayerRepo

class HomeViewModel(private val playerRepo: PlayerRepo) : ViewModel() {
    fun getPlayerName() = playerRepo.getPlayerData().name
}