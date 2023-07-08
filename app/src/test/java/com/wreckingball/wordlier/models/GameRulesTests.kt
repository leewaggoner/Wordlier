package com.wreckingball.wordlier.models

import com.wreckingball.wordlier.di.appModule
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
            Pair('T', CorrectLetterCell),
            Pair('R', CorrectLetterCell),
            Pair('U', CorrectLetterCell),
            Pair('S', CorrectLetterCell),
            Pair('T', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("TRUST", "TRUST")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Guess_With_Multiple_Letter_Last() {
        val correctResult = listOf(
            Pair('T', WrongLetterCell),
            Pair('R', WrongLetterCell),
            Pair('U', CorrectLetterCell),
            Pair('S', WrongLetterCell),
            Pair('T', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("FAULT", "TRUST")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Guess_With_Multiple_Letter_First() {
        val correctResult = listOf(
            Pair('D', WrongLetterCell),
            Pair('R', CorrectLetterCell),
            Pair('E', WrongLetterCell),
            Pair('S', CorrectLetterCell),
            Pair('S', WrongLetterCell),
        )
        val result = gameRules.colorLetters("TRUST", "DRESS")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Triple_letter_Guess() {
        val correctResult = listOf(
            Pair('B', WrongPositionCell),
            Pair('O', WrongLetterCell),
            Pair('B', CorrectLetterCell),
            Pair('B', WrongLetterCell),
            Pair('Y', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("ABBEY", "BOBBY")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun colorLetters_Triple_letter_Word() {
        val correctResult = listOf(
            Pair('A', WrongLetterCell),
            Pair('B', WrongPositionCell),
            Pair('B', CorrectLetterCell),
            Pair('E', WrongLetterCell),
            Pair('Y', CorrectLetterCell),
        )
        val result = gameRules.colorLetters("BOBBY", "ABBEY")
        Assert.assertEquals(correctResult, result)
    }

    @Test
    fun handleGuess_Game_Is_Won() {
        val result = gameRules.handleGuess("TRUST", "TRUST", 1)
        Assert.assertEquals(GameResult.WIN, result)
    }

    @Test
    fun handleGuess_Game_Is_Lost() {
        val result = gameRules.handleGuess("TRUST", "FAULT", MAX_GUESSES)
        Assert.assertEquals(GameResult.LOSS, result)
    }

    @Test
    fun handleGuess_Game_Next_Guess() {
        val result = gameRules.handleGuess("TRUST", "FAULT", 2)
        Assert.assertEquals(GameResult.NEXT_GUESS, result)
    }
}