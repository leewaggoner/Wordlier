package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row {
            val keys = "QWERTYUIOP"
            CharacterCell(
                letter = "Q",
                onClick = onClick,
            )
            CharacterCell(
                letter = "<-",
                onClick = onClick,
            )
        }
    }
}

@Preview
@Composable
fun KeyboardPreview() {
    Keyboard(
        onClick = { }
    )
}