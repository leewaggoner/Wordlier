package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.ui.theme.NormalCell

@Composable
fun GameBoard(
    guesses: List<List<Pair<Char, Color>>>,
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
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
            listOf(
                Pair('S', NormalCell),
                Pair('W', NormalCell),
                Pair('O', NormalCell),
                Pair('R', NormalCell),
                Pair('D', NormalCell),
            ),
        )
    )
}