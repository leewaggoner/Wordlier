package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.ui.theme.ResultBarColor

@Composable
fun GuessDistribution(
    modifier: Modifier = Modifier,
    guessIndex: Int,
    maxGuesses: Int,
    guesses: Int,
    barColor: Color,
) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(14.dp)
        )
    ) {
        Text(
            modifier = Modifier
                .width(16.dp),
            text = guessIndex.toString(),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(getFillPercent(guesses, maxGuesses))
                .fillMaxSize()
                .background(barColor)
            )
            Box(
                modifier = Modifier
                    .background(barColor),
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = guesses.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

private fun getFillPercent(guesses: Int, maxGuesses: Int) : Float {
    var fillPercent = 0.0f
    if (maxGuesses > 0.0) {
        fillPercent = (guesses.toFloat() / maxGuesses.toFloat())
    }
    return fillPercent
}

@Preview
@Composable
fun GuessDistributionPreview() {
    val results = GameResults(
        guesses = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 5,
    )

    GuessDistribution(
        guessIndex = 1,
        maxGuesses = results.maxGuesses,
        guesses = results.guesses[0],
        barColor = ResultBarColor
    )
}