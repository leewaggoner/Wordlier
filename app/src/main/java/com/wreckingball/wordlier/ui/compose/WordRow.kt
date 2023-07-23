package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Composable
fun WordRow(
    modifier: Modifier = Modifier,
    shake: Boolean,
    onShakeFinished: () -> Unit,
    flipIndex: Int = -1,
    onFlipFinished: () -> Unit,
    guess: List<GameLetter>,
) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(
                start = 8.dp,
                top = 4.dp,
                end = 8.dp,
                bottom = 4.dp,
            )
            .shake(shake) { onShakeFinished() },
        ),
        horizontalArrangement = Arrangement.Center,
    ) {
        for ((index, character) in guess.withIndex()) {
            FlippableCharacterCell(
                letter = character.first.toString(),
                color = character.second,
                flip = flipIndex in 0 until MAX_WORD_LENGTH && flipIndex == index,
                onFlipFinished = onFlipFinished,
            )
            if (index < guess.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
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