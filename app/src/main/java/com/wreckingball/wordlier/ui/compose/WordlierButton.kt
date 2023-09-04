package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun WordlierButton(
    text: String,
    action: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier.then(Modifier
            .fillMaxWidth()
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            modifier = Modifier
                .size(
                    width = MaterialTheme.dimensions.ButtonWidth,
                    height = MaterialTheme.dimensions.ButtonHeight,
                ),
            onClick = { action() },
            enabled = enabled,
        ) {
            Text(text = text)
        }
    }
}

@Preview(name = "Wordlier Button")
@Composable
fun WordlierButtonPreview() {
    WordlierButton(
        text = stringResource(id = R.string.how_to_play),
        action = { },
    )
}