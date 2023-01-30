package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WordRow(
    guess: List<String>,
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
            CharacterCell(letter = character)
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
        guess = listOf("W", "A", "R", "T", "S")
    )
}