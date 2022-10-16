package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameBoard(
    guesses: List<List<String>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (guess in guesses) {
            WordRow(guess = guess)
        }
    }
}

@Preview
@Composable
fun GameBoardPreview() {
    GameBoard(
        guesses = listOf(
            listOf("S", "W", "O", "R", "D"),
            listOf("S", "W", "O", "R", "D"),
            listOf("S", "W", "O", "R", "D"),
            listOf("S", "W", "O", "R", "D"),
            listOf("S", "W", "O", "R", "D"),
            listOf("S", "W", "O", "R", "D"),
        )
    )
}