package com.wreckingball.wordlier.repositories

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.utils.DataStoreWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val DATA_STORE_NAME = "TestDataStore"

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GameResultsRepoTest {
    private val testContext = ApplicationProvider.getApplicationContext<Context>()
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val gameResultsRepo = GameResultsRepo(
        DataStoreWrapper(
            PreferenceDataStoreFactory.create(
                produceFile = { testContext.preferencesDataStoreFile(DATA_STORE_NAME) },
                scope = testCoroutineScope
            )
        )
    )
    private val expectedInitialGameResults = GameResults(
        winsPerRound = listOf(0, 0, 0, 0, 0, 0),
        gamesPlayed = 0,
        winPercent = 0,
        currentStreak = 0,
        maxStreak = 0,
        lastRoundWon = -1,
    )
    private val expectedSingleGameWonResults = GameResults(
        winsPerRound = listOf(1, 0, 0, 0, 0, 0),
        gamesPlayed = 1,
        winPercent = 100,
        currentStreak = 1,
        maxStreak = 1,
        lastRoundWon = 0,
    )
    private val expectedSingleGameLostResults = GameResults(
        winsPerRound = listOf(0, 0, 0, 0, 0, 0),
        gamesPlayed = 1,
        winPercent = 0,
        currentStreak = 0,
        maxStreak = 0,
        lastRoundWon = -1,
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun testGetInitialGameResults() {
        testCoroutineScope.runTest {
            gameResultsRepo.clearAll()
            assertThat(gameResultsRepo.getGameResults()).isEqualTo(expectedInitialGameResults)
        }
    }

    @Test
    fun testUpdateGameResultsWon() {
        testCoroutineScope.runTest {
            gameResultsRepo.clearAll()
            gameResultsRepo.updateGameResults(true, 0)
            assertThat(gameResultsRepo.getGameResults()).isEqualTo(expectedSingleGameWonResults)
        }

    }

    @Test
    fun testUpdateGameResultsLost() {
        testCoroutineScope.runTest {
            gameResultsRepo.clearAll()
            gameResultsRepo.updateGameResults(false, 5)
            assertThat(gameResultsRepo.getGameResults()).isEqualTo(expectedSingleGameLostResults)
        }
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineScope.cancel()
    }
}