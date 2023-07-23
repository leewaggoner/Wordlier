package com.wreckingball.wordlier.models

import com.wreckingball.wordlier.di.appModule
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.domain.GameResult
import com.wreckingball.wordlier.domain.GameRules
import com.wreckingball.wordlier.domain.MAX_GUESSES
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class GameRulesTests : KoinTest {
    private val gameRules by inject <GameRules>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(appModule)
    }

    @Test
    fun colorLetters_All_Correct() {
        val correctResult = listOf(
            GameLetter('T', CorrectLetterCell),
            GameLetter('R', CorrectLetterCell),
            GameLetter('U', CorrectLetterCell),
            GameLetter('S', CorrectLetterCell),
            GameLetter('T', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("TRUST", "TRUST")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Guess_With_Multiple_Letter_Last() {
        val correctResult = listOf(
            GameLetter('T', WrongLetterCell),
            GameLetter('R', WrongLetterCell),
            GameLetter('U', CorrectLetterCell),
            GameLetter('S', WrongLetterCell),
            GameLetter('T', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("FAULT", "TRUST")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Guess_With_Multiple_Letter_First() {
        val correctResult = listOf(
            GameLetter('D', WrongLetterCell),
            GameLetter('R', CorrectLetterCell),
            GameLetter('E', WrongLetterCell),
            GameLetter('S', CorrectLetterCell),
            GameLetter('S', WrongLetterCell),
        )
        val result = gameRules.colorLetters("TRUST", "DRESS")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Triple_letter_Guess() {
        val correctResult = listOf(
            GameLetter('B', WrongPositionCell),
            GameLetter('O', WrongLetterCell),
            GameLetter('B', CorrectLetterCell),
            GameLetter('B', WrongLetterCell),
            GameLetter('Y', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("ABBEY", "BOBBY")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Triple_letter_Word() {
        val correctResult = listOf(
            GameLetter('A', WrongLetterCell),
            GameLetter('B', WrongPositionCell),
            GameLetter('B', CorrectLetterCell),
            GameLetter('E', WrongLetterCell),
            GameLetter('Y', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("BOBBY", "ABBEY")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun handleGuess_Game_Is_Won() {
        val result = gameRules.handleGuess("TRUST", "TRUST", 1, listOf())
        Assert.assertTrue(result is GameResult.Win)
    }

    @Test
    fun handleGuess_Game_Is_Lost() {
        val result = gameRules.handleGuess("TRUST", "FAULT", MAX_GUESSES, listOf())
        Assert.assertTrue(result is GameResult.Loss)
    }

    @Test
    fun handleGuess_Game_Next_Guess() {
        val result = gameRules.handleGuess("TRUST", "FAULT", 2, listOf())
        Assert.assertTrue(result is GameResult.NextGuess)
    }
}