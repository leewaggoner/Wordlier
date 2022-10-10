package com.wreckingball.wordlier.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.viewModel

@Composable
fun Home(
    viewModel: HomeViewModel
) {
    HomeContent(
        name = viewModel.getPlayerName()
    )
}

@Composable
fun HomeContent(
    name: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.h3
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h4
        )
    }
}

@Preview(name = "Home Content")
@Composable
fun HomeContentPreview() {
    HomeContent(
        name = "Lee J Waggoner"
    )
}
