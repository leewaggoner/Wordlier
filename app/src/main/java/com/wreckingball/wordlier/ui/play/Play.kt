package com.wreckingball.wordlier.ui.play.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingball.wordlier.Actions
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.compose.WordlierButton
import com.wreckingball.wordlier.ui.play.PlayViewModel
import com.wreckingball.wordlier.ui.theme.dimensions
import org.koin.androidx.compose.getViewModel

@Composable
fun Play(
    actions: Actions,
    viewModel: PlayViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { event ->
        event.consume { navigation ->
            when (navigation) {
                PlayNavigation.GoToGame -> {
                    actions.navigateToGame()
                }
            }
        }
    }

    PlayContent(
        userName = viewModel.userName,
        playGame = viewModel::playGame,
    )
}

@Composable
fun PlayContent(
    userName: String,
    playGame: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.dimensions.IntroPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.new_puzzle_ready, userName),
            textAlign = TextAlign.Center
        )

        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.IntroBigSpace),
            text = stringResource(id = R.string.play),
            action = playGame
        )
    }
}

@Preview(name = "Play content preview")
@Composable
fun PlayContentPreview() {
    PlayContent(
        userName = "Lee",
        playGame = { },
    )
}