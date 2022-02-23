package com.wreckingball.wordlier.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.repositories.PlayerRepo
import org.koin.androidx.compose.get
import org.koin.androidx.compose.viewModel

@Composable

fun Login(goHome: () -> Unit) {
    val viewModel: LoginViewModel by viewModel()
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = context.getString(R.string.app_name),
            style = MaterialTheme.typography.h3
        )
        LoginFields(
            context = context,
            name = name,
            goHome = goHome,
            onNameChange = { name = it },
            saveName = { viewModel.setPlayerName(it) }
        )
    }
}

@Composable
private fun LoginFields(
    context: Context,
    name: String,
    goHome: () -> Unit,
    onNameChange: (String) -> Unit,
    saveName: (String) ->Unit
) {
    OutlinedTextField(
        value = name,
        placeholder = { Text(text = context.getString(R.string.player_name_hint)) },
        label = { Text(text = context.getString(R.string.player_name_label)) },
        onValueChange = onNameChange
    )
    Button(onClick = {
        if (name.isBlank()) {
            Toast.makeText(context, context.getText(R.string.player_name_error), Toast.LENGTH_LONG).show()
        } else {
            saveName(name)
            goHome()
        }
    }) {
        Text(text = context.getString(R.string.start_button_text), style = MaterialTheme.typography.button)
    }
}


