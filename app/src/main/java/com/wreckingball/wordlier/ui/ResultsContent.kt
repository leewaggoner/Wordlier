package com.wreckingball.wordlier.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.domain.GameResults
import com.wreckingball.wordlier.ui.compose.GuessDistributionSection
import com.wreckingball.wordlier.ui.compose.StatisticsSection

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
        StatisticsSection(
            modifier = Modifier
                .padding(bottom = 8.dp),
            gameResults = gameResults,
        )

        GuessDistributionSection(
            modifier = Modifier
                .padding(bottom = 56.dp),
            gameResults = gameResults,
        )
    }
}

@Preview
@Composable
fun ResultsContentPreview() {
    val results = GameResults(
        winsPerRound = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 4,
    )

    ResultsContent(gameResults = results)
}
