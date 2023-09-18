package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.domain.MAX_WORD_LENGTH
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun WordRow(
    modifier: Modifier = Modifier,
    shake: Boolean,
    onShakeFinished: () -> Unit,
    waveIndex: Int,
    onWaveFinished: () -> Unit,
    flipIndex: Int = -1,
    onFlipFinished: () -> Unit,
    guess: List<GameLetter>,
) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(
                start = MaterialTheme.dimensions.LetterHorizontalPadding,
                top = MaterialTheme.dimensions.LetterVerticalPadding,
                end = MaterialTheme.dimensions.LetterHorizontalPadding,
                bottom = MaterialTheme.dimensions.LetterVerticalPadding,
            )
            .shake(shake) { onShakeFinished() },
        ),
        horizontalArrangement = Arrangement.Center,
    ) {
        for ((index, character) in guess.withIndex()) {
            FlippableCharacterCell(
                letter = character.letter.toString(),
                color = character.color,
                wave = waveIndex in 0 until MAX_WORD_LENGTH && waveIndex == index,
                onWaveFinished = onWaveFinished,
                flip = flipIndex in 0 until MAX_WORD_LENGTH && flipIndex == index,
                onFlipFinished = onFlipFinished,
            )
            if (index < guess.size - 1) {
                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.GuessRowPaddingDims))
            }
        }
    }
}

@Preview
@Composable
fun WordRowPreview() {
    WordRow(
        shake = false,
        onShakeFinished = { },
        waveIndex = -1,
        onWaveFinished = { },
        onFlipFinished = { },
        modifier = Modifier.width(800.dp),
        guess = listOf(
            GameLetter('W', CorrectLetterCell),
            GameLetter('A', WrongLetterCell),
            GameLetter('R', WrongPositionCell),
            GameLetter('T', NormalCell),
            GameLetter('S', NormalCell),
        )
    )
}