package com.wreckingball.wordlier.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.models.GameState
import com.wreckingball.wordlier.models.GameplayState
import com.wreckingball.wordlier.ui.compose.GameBoard
import com.wreckingball.wordlier.ui.theme.Purple500
import com.wreckingball.wordlier.ui.theme.Teal200
import com.wreckingball.wordlier.ui.theme.Typography

@Composable
fun Game(
    viewModel: GameViewModel
) {
    GameContent(
        state = viewModel.gameState
    )
}

@Composable
fun GameContent(
    state: GameState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Teal200),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.app_name).uppercase(),
                textAlign = TextAlign.Center,
                style = Typography.h2,
                fontWeight = FontWeight.Bold,
                color = Purple500
            )
            GameBoard(
                modifier = Modifier
                    .padding(top = 32.dp),
                guesses = state.board.guesses
            )
        }
    }
}

@Preview(name = "Home Content")
@Composable
fun HomeContentPreview() {
    GameContent(
        state = GameState(state = GameplayState.START_ROUND)
    )
}
