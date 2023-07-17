package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.domain.MAX_GUESSES
import com.wreckingball.wordlier.ui.theme.NormalCell

@Composable
fun GameBoard(
    guesses: List<List<Pair<Char, Color>>>,
    shakeRow: Int,
    onShakeFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        guesses.forEachIndexed { index, guess ->
            WordRow(
                shake = shakeRow in 0 until MAX_GUESSES && shakeRow == index,
                onShakeFinished = onShakeFinished,
                guess = guess
            )
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
        ),
        shakeRow = -1,
        onShakeFinished = { }
    )
}