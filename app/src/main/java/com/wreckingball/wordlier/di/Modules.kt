package com.wreckingball.wordlier.di

import android.content.Context
import android.content.SharedPreferences
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.utils.PreferencesWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val REPO_NAME = "com.wreckingball.wordlier"

val appModule = module {
    single { PlayerRepo( get()) }
    single { PreferencesWrapper(getSharedPrefs(androidContext())) }
}

private fun getSharedPrefs(context: Context) : SharedPreferences {
    return context.getSharedPreferences(REPO_NAME, Context.MODE_PRIVATE)
}
