package com.wreckingball.wordlier.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.wreckingball.wordlier.BuildConfig
import com.wreckingball.wordlier.models.GameCursor
import com.wreckingball.wordlier.models.GamePlay
import com.wreckingball.wordlier.models.GameRules
import com.wreckingball.wordlier.network.WordValidationService
import com.wreckingball.wordlier.repositories.GameRepo
import com.wreckingball.wordlier.repositories.PlayerRepo
import com.wreckingball.wordlier.ui.game.GameViewModel
import com.wreckingball.wordlier.ui.login.LoginViewModel
import com.wreckingball.wordlier.utils.DataStoreWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val DATA_STORE_NAME = "com.wreckingball.wordlier"
private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L

val appModule = module {
    viewModel { LoginViewModel(playerRepo = get()) }
    viewModel { GameViewModel(gamePlay = get()) }

    factory { PlayerRepo(dataStore = get()) }
    factory { GameRepo(wordValidationService = get()) }
    factory { GameRules() }
    single {
        GamePlay(
            cursor = get(),
            gameRepo = get(),
            gameRules = get(),
        )
    }
    single { GameCursor() }
    single<WordValidationService> {
        createService(
            retrofit = retrofitService(
                url = BuildConfig.WORD_VALIDATION_URL,
                okHttpClient = okHttp(),
                converterFactory = GsonConverterFactory.create()
            )
        )
    }
    single { DataStoreWrapper(getDataStore(androidContext())) }
}

inline fun <reified T> createService(retrofit: Retrofit) : T = retrofit.create(T::class.java)

private fun getSharedPrefs(context: Context) : SharedPreferences {
    return context.getSharedPreferences(DATA_STORE_NAME, Context.MODE_PRIVATE)
}

private fun getDataStore(context: Context) : DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

private fun retrofitService(
    url: String,
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
) = Retrofit.Builder().apply {
    baseUrl(url)
    client(okHttpClient)
    addConverterFactory(converterFactory)
}.build()

private fun okHttp() = OkHttpClient.Builder().apply {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    addInterceptor(interceptor)
    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    connectTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    retryOnConnectionFailure(true)
}.build()

