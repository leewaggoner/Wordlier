package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.ui.theme.ResultBarColor

@Composable
fun GuessDistribution(
    modifier: Modifier = Modifier,
    round: Int,
    maxWins: Int,
    wins: Int,
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
                .padding(end = 8.dp),
            text = round.toString(),
            style = MaterialTheme.typography.labelSmall,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(getFillPercent(wins, maxWins))
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
                    text = wins.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

private fun getFillPercent(wins: Int, maxWins: Int) : Float {
    var fillPercent = 0.0f
    if (maxWins > 0.0) {
        fillPercent = (wins.toFloat() / maxWins.toFloat())
    }
    return fillPercent
}

@Preview
@Composable
fun GuessDistributionPreview() {
    val results = GameResults(
        winsPerRound = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 5,
    )

    GuessDistribution(
        round = 1,
        maxWins = results.maxWins,
        wins = results.winsPerRound[1],
        barColor = ResultBarColor
    )
}