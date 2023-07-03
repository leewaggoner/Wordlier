package com.wreckingball.wordlier.models

import androidx.compose.ui.graphics.Color
import com.wreckingball.wordlier.di.appModule
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class GamePlayTests : KoinTest {
    private val gamePlay by inject <GamePlay>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(appModule)
    }

    @Test
    fun colorLetters_All_Correct() {
        val correctResult = listOf(
            Pair('T', CorrectLetterCell),
            Pair('R', CorrectLetterCell),
            Pair('U', CorrectLetterCell),
            Pair('S', CorrectLetterCell),
            Pair('T', CorrectLetterCell),
        )
        val result = gamePlay.colorLetters("TRUST", "TRUST")
        Assert.assertEquals(correctResult, result)
    }
}