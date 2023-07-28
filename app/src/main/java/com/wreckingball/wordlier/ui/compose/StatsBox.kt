package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.GameResults

@Composable
fun StatsBox(
    modifier: Modifier = Modifier,
    statNumber: Int,
    statName: String,
) {
    Column(
        modifier = modifier.then(
            Modifier
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = statNumber.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = statName,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp
        )
    }
}

@Preview
@Composable
fun StatsBoxPreview() {
    val results = GameResults(
        winsPerRound = listOf(0, 9, 36, 92, 97, 57),
        gamesPlayed = 316,
        winPercent = 92,
        currentStreak = 4,
        maxStreak = 36,
        lastRoundWon = 5,
    )

    StatsBox(
        statNumber = results.currentStreak,
        statName = stringResource(id = R.string.currentStreak)
    )
}