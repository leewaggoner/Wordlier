package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.domain.MAX_GUESSES
import com.wreckingball.wordlier.ui.theme.NormalCell

@Composable
fun GameBoard(
    guesses: List<List<GameLetter>>,
    shakeRow: Int,
    onShakeFinished: () -> Unit,
    waveRow: Int,
    waveIndex: Int,
    onWaveFinished: () -> Unit,
    flipRow: Int,
    flipIndex: Int,
    onFlipFinished: () -> Unit,
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
                waveIndex = if (waveRow == index) waveIndex else -1,
                onWaveFinished = onWaveFinished,
                flipIndex = if (flipRow == index) flipIndex else -1,
                onFlipFinished = onFlipFinished,
                guess = guess,
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
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
            listOf(
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
            listOf(
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
            listOf(
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
            listOf(
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
            listOf(
                GameLetter('S', NormalCell),
                GameLetter('W', NormalCell),
                GameLetter('O', NormalCell),
                GameLetter('R', NormalCell),
                GameLetter('D', NormalCell),
            ),
        ),
        shakeRow = -1,
        onShakeFinished = { },
        waveRow = -1,
        waveIndex = -1,
        onWaveFinished = { },
        flipRow = -1,
        flipIndex = -1,
        onFlipFinished = { },
    )
}