package com.wreckingball.wordlier.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.domain.GameState
import com.wreckingball.wordlier.ui.ResultsContent
import com.wreckingball.wordlier.ui.compose.GameBoard
import com.wreckingball.wordlier.ui.compose.Keyboard
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.Purple500
import com.wreckingball.wordlier.ui.theme.Teal200
import com.wreckingball.wordlier.ui.theme.Typography
import com.wreckingball.wordlier.ui.theme.dimensions
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(
    viewModel: GameViewModel = getViewModel()
) {
    val resultsState =  rememberBottomSheetScaffoldState()

    if (viewModel.state.showResults) {
        LaunchedEffect(key1 = Unit) {
            resultsState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = resultsState,
        sheetPeekHeight = 32.dp,
        sheetContent = {
            viewModel.state.gameResults?.let { results ->
                ResultsContent(
                    gameResults = results,
                )
            }
        }
    ) {
        GameContent(
            state = viewModel.state,
            onShakeFinished = viewModel::onShakeFinished,
            onWaveFinished = viewModel::onWaveFinished,
            onFlipFinished = viewModel::onFlipFinished,
            onKeyboardClick = viewModel::onKeyboardClick,
        )

        if (viewModel.state.msgId > 0) {
            val msg = if (viewModel.state.msg.isNotEmpty()) {
                viewModel.state.msg
            } else {
                stringResource(id = viewModel.state.msgId)
            }
            LaunchedEffect(key1 = Unit) {
                val snackbarResult = resultsState.snackbarHostState.showSnackbar(
                    message = msg,
                    duration = viewModel.state.msgDuration,
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> viewModel.clearErrorMsg()
                    else -> { }
                }
            }
        }
    }
}

@Composable
fun GameContent(
    state: GameState,
    onShakeFinished: () -> Unit,
    onWaveFinished: () -> Unit,
    onFlipFinished: () -> Unit,
    onKeyboardClick: (String) -> Unit,
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
                style = Typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Purple500
            )
            GameBoard(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.GameBoardPadding)
                    .fillMaxWidth(),
                shakeRow = state.shakeRow,
                onShakeFinished = onShakeFinished,
                waveRow = state.waveRow,
                waveIndex = state.waveIndex,
                onWaveFinished = onWaveFinished,
                flipRow = state.flipRow,
                flipIndex = state.flipIndex,
                onFlipFinished = onFlipFinished,
                guesses = state.board,
            )
            Keyboard(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.GameBoardPadding)
                    .fillMaxWidth(),
                usedLetters = state.usedLetters,
                onClick = onKeyboardClick
            )
        }
    }

    if (state.loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { },
                )
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(name = "Home Content")
@Composable
fun HomeContentPreview() {
    GameContent(
        state = GameState(
            board = remember {
                mutableStateListOf(
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                    mutableStateListOf(
                        GameLetter('S', NormalCell),
                        GameLetter('L', NormalCell),
                        GameLetter('A', NormalCell),
                        GameLetter('T', NormalCell),
                        GameLetter('E', NormalCell),
                    ),
                )
            }
        ),
        onShakeFinished = { },
        onWaveFinished = { },
        onFlipFinished = { },
        onKeyboardClick = { },
    )
}
