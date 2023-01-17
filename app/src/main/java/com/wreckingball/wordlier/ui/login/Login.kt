package com.wreckingball.wordlier.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingball.wordlier.Actions
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.login.model.LoginState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun Login(
    actions: Actions,
    viewModel: LoginViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { event ->
        event.consume { navigation ->
            when (navigation) {
                LoginNavigation.GoToHome -> {
                    actions.navigateToGame()
                }
            }
        }
    }

    LoginContent(
        screenState = viewModel.state,
        onNameChange = viewModel::onNameChange,
        setPlayerName = viewModel::setPlayerData,
    )
}

@Composable
fun LoginContent(
    screenState: LoginState,
    onNameChange: (String) -> Unit,
    setPlayerName: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h3
        )
        LoginFields(
            screenState = screenState,
            onNameChange = onNameChange,
            saveDataAndProceed = setPlayerName
        )
    }
}

@Composable
private fun LoginFields(
    screenState: LoginState,
    onNameChange: (String) -> Unit,
    saveDataAndProceed: () ->Unit
) {
    OutlinedTextField(
        value = screenState.name,
        placeholder = { Text(text = stringResource(id = R.string.player_name_hint)) },
        label = { Text(text = stringResource(id = R.string.player_name_label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                saveDataAndProceed()
            }
        ),
        onValueChange = onNameChange,
    )
    Button(
        enabled = screenState.buttonEnabled,
        onClick = saveDataAndProceed,
    ) {
        Text(text = stringResource(id = R.string.start_button_text),
            style = MaterialTheme.typography.button
        )
    }
}

@Preview(name = "Login Content")
@Composable
fun LoginContentPreview() {
    val screenState = LoginState(name = "Lee", buttonEnabled = true)
    LoginContent(
        screenState = screenState,
        { },
        { }
    )
}


