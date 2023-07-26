package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
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
import com.wreckingball.wordlier.ui.theme.CurResultBarColor
import com.wreckingball.wordlier.ui.theme.ResultBarColor

@Composable
fun GuessDistributionSection(
    modifier: Modifier = Modifier,
    gameResults: GameResults,
) {
    Column(
        modifier = modifier.then(
            Modifier
        )
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.guessDistribution),
            style = MaterialTheme.typography.titleSmall,
        )
        gameResults.winsPerRound.forEachIndexed { index, i ->
            GuessDistribution(
                modifier = Modifier.padding(bottom = 4.dp),
                round = index + 1,
                maxWins = gameResults.maxWins,
                wins = i,
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
fun GuessDistributionSectionPreview() {
    val results = GameResults(
        winsPerRound = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 5,
    )
    GuessDistributionSection(gameResults = results)
}