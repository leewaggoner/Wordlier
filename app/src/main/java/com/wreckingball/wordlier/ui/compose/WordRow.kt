package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

@Composable
fun WordRow(
    guess: List<Pair<Char, Color>>,
    modifier: Modifier = Modifier
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
        ),
        horizontalArrangement = Arrangement.Center,
    ) {
        for ((index, character) in guess.withIndex()) {
            CharacterCell(
                letter = character.first.toString(),
                color = character.second
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
        modifier = Modifier.width(800.dp),
        guess = listOf(
            Pair('W', CorrectLetterCell),
            Pair('A', WrongLetterCell),
            Pair('R', WrongPositionCell),
            Pair('T', NormalCell),
            Pair('S', NormalCell),
        )
    )
}