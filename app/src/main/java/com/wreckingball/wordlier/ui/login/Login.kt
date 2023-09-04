package com.wreckingball.wordlier.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingball.wordlier.Actions
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.compose.WordlierButton
import com.wreckingball.wordlier.ui.login.model.LoginNavigation
import com.wreckingball.wordlier.ui.login.model.LoginState
import com.wreckingball.wordlier.ui.theme.dimensions
import org.koin.androidx.compose.getViewModel

@Composable
fun Login(
    actions: Actions,
    viewModel: LoginViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { event ->
        event.consume { navigation ->
            when (navigation) {
                LoginNavigation.GoToGame -> {
                    actions.navigateToGame()
                }
            }
        }
    }

    LoginContent(
        screenState = viewModel.state,
        onNameChange = viewModel::onNameChange,
        loginAndPlay = viewModel::loginAndPlay,
        createAccountAndPlay = viewModel::createAccountAndPlay,
    )
}

@Composable
fun LoginContent(
    screenState: LoginState,
    onNameChange: (String) -> Unit,
    loginAndPlay: () -> Unit,
    createAccountAndPlay: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displaySmall
        )
        LoginFields(
            screenState = screenState,
            onNameChange = onNameChange,
            loginAndPlay = loginAndPlay,
            createAccountAndPlay = createAccountAndPlay,
        )
    }
}

@Composable
private fun LoginFields(
    screenState: LoginState,
    onNameChange: (String) -> Unit,
    loginAndPlay: () ->Unit,
    createAccountAndPlay: () -> Unit,
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
                loginAndPlay()
            }
        ),
        onValueChange = onNameChange,
    )
    if (screenState.accountCreated) {
        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.login),
            action = loginAndPlay,
            enabled = screenState.buttonEnabled,
        )
    } else {
        WordlierButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.create_account),
            action = createAccountAndPlay,
            enabled = screenState.buttonEnabled,
        )
    }
}

@Preview(name = "Login Content")
@Composable
fun LoginContentPreview() {
    val screenState = LoginState(
        name = "Lee",
        buttonEnabled = true,
        accountCreated = false
    )
    LoginContent(
        screenState = screenState,
        { },
        { },
        { },
    )
}


