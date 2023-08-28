package com.wreckingball.wordlier.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingball.wordlier.Actions
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.compose.WordlierButton
import com.wreckingball.wordlier.ui.intro.model.IntroNavigation
import com.wreckingball.wordlier.ui.theme.dimensions
import org.koin.androidx.compose.getViewModel

@Composable
fun Intro(
    actions: Actions,
    viewModel: IntroViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { event ->
        event.consume { navigation ->
            when (navigation) {
                IntroNavigation.GoToGame -> {
                    actions.navigateToGame()
                }
                IntroNavigation.Login -> {
                    actions.navigateToLogin()
                }
                IntroNavigation.HowToPlay -> {
                    actions.navigateToHowToPlay()
                }
            }
        }
    }

    IntroContent(
        playGame = viewModel::playGame,
        login = viewModel::login,
        howToPlay = viewModel::howToPlay,
        state = viewModel.state,
    )
}

@Composable
private fun IntroContent(
    playGame: () -> Unit,
    login: () -> Unit,
    howToPlay: () -> Unit,
    state: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimensions.IntroPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(MaterialTheme.dimensions.IntroIconSize),
            painter = painterResource(id = R.drawable.ic_wordlier),
            contentDescription = stringResource(id = R.string.app_name),
        )
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.app_name),
            fontSize = MaterialTheme.dimensions.IntroTitleSize,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace)
                .fillMaxWidth(),
            text = stringResource(id = R.string.game_description),
            fontSize = MaterialTheme.dimensions.IntroDescriptionSize,
            textAlign = TextAlign.Center
        )
        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.IntroBigSpace),
            text = stringResource(id = R.string.play),
            action = playGame,
        )
        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.login),
            action = login,
        )
        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.how_to_play),
            action = howToPlay,
        )
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.IntroBigSpace)
                .fillMaxWidth(),
            text = state,
            fontSize = MaterialTheme.dimensions.IntroNormalTextSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview(name = "Intro Content")
@Composable
fun IntroContentPreview() {

    IntroContent(
        playGame = { },
        login = { },
        howToPlay = { },
        state = "August 28, 2023",
    )
}