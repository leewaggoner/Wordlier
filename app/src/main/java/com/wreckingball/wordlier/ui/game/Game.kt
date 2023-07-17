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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import com.wreckingball.wordlier.domain.GameState
import com.wreckingball.wordlier.ui.compose.GameBoard
import com.wreckingball.wordlier.ui.compose.Keyboard
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.Purple500
import com.wreckingball.wordlier.ui.theme.Teal200
import com.wreckingball.wordlier.ui.theme.Typography
import org.koin.androidx.compose.getViewModel

@Composable
fun Game(
    viewModel: GameViewModel = getViewModel()
) {
    GameContent(
        state = viewModel.state,
        onShakeFinished = viewModel::onShakeFinished,
        onKeyboardClick = viewModel::onKeyboardClick,
        clearErrorMsg = viewModel::clearErrorMsg,
    )
}

@Composable
fun GameContent(
    state: GameState,
    onShakeFinished: () -> Unit,
    onKeyboardClick: (String) -> Unit,
    clearErrorMsg: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
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
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    shakeRow = state.shakeRow,
                    onShakeFinished = onShakeFinished,
                    guesses = state.board,
                )
                Keyboard(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
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

        if (state.errMsgId > 0) {
            val msg = stringResource(id = state.errMsgId)
            LaunchedEffect(key1 = Unit) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = msg,
                    duration = SnackbarDuration.Short,
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> clearErrorMsg()
                    else -> { }
                }
            }
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
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                    mutableStateListOf(
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                    mutableStateListOf(
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                    mutableStateListOf(
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                    mutableStateListOf(
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                    mutableStateListOf(
                        Pair('S', NormalCell),
                        Pair('L', NormalCell),
                        Pair('A', NormalCell),
                        Pair('T', NormalCell),
                        Pair('E', NormalCell),
                    ),
                )
            }
        ),
        onShakeFinished = { },
        onKeyboardClick = { },
        clearErrorMsg = { }
    )
}
