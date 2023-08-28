package com.wreckingball.wordlier.ui.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingball.wordlier.ui.intro.model.IntroNavigation
import com.wreckingball.wordlier.utils.OneShotEvent
import com.wreckingball.wordlier.utils.introDateToString
import com.wreckingball.wordlier.utils.toEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.Date

class IntroViewModel : ViewModel() {
    val navigation = MutableSharedFlow<OneShotEvent<IntroNavigation>?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    val state by mutableStateOf(Date().introDateToString())

    fun playGame(){
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(IntroNavigation.GoToGame.toEvent())
        }
    }

    fun login(){
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(IntroNavigation.Login.toEvent())
        }
    }

    fun howToPlay(){
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(IntroNavigation.HowToPlay.toEvent())
        }
    }
}