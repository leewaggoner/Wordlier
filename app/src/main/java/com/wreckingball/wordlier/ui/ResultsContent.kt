package com.wreckingball.wordlier.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.ui.compose.GuessDistribution
import com.wreckingball.wordlier.ui.compose.StatsBox
import com.wreckingball.wordlier.ui.theme.CurResultBarColor
import com.wreckingball.wordlier.ui.theme.ResultBarColor

@Composable
fun ResultsContent (
    modifier: Modifier = Modifier,
    gameResults: GameResults,
) {
    Column(
        modifier = modifier.then(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
        )
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.statistics),
            style = MaterialTheme.typography.titleSmall,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            StatsBox(
                statNumber = gameResults.gamesPlayed,
                statName = stringResource(id = R.string.played)
            )
            StatsBox(
                statNumber = gameResults.winPercent,
                statName = stringResource(id = R.string.winPercent)
            )
            StatsBox(
                statNumber = gameResults.currentStreak,
                statName = stringResource(id = R.string.currentStreak)
            )
            StatsBox(
                statNumber = gameResults.maxStreak,
                statName = stringResource(id = R.string.maxStreak)
            )
        }

        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.guessDistribution),
            style = MaterialTheme.typography.titleSmall,
        )
        gameResults.guesses.forEachIndexed { index, i ->
            GuessDistribution(
                modifier = Modifier.padding(bottom = 4.dp),
                guessIndex = index + 1,
                maxGuesses = gameResults.maxGuesses,
                guesses = i,
                barColor = if (index == gameResults.lastRoundWon) {
                    CurResultBarColor
                } else {
                    ResultBarColor
                }
            )
        }
    }
}

@Preview
@Composable
fun ResultsContentPreview() {
    val results = GameResults(
        guesses = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 4,
    )

    ResultsContent(gameResults = results)
}
