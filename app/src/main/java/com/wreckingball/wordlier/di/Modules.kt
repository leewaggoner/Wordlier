package com.wreckingball.wordlier.di

import android.content.Context
import android.content.SharedPreferences
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.game.GameViewModel
import com.wreckingball.wordlier.ui.login.LoginViewModel
import com.wreckingball.wordlier.utils.PreferencesWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val REPO_NAME = "com.wreckingball.wordlier"

val appModule = module() {
    viewModel { LoginViewModel(playerRepo = get()) }
    viewModel { GameViewModel(playerRepo = get()) }

    single { PlayerRepo( preferencesWrapper = get()) }
    single { PreferencesWrapper(getSharedPrefs(androidContext())) }
}

private fun getSharedPrefs(context: Context) : SharedPreferences {
    return context.getSharedPreferences(REPO_NAME, Context.MODE_PRIVATE)
}
