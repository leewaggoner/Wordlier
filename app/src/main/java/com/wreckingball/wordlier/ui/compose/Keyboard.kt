package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.BACK
import com.wreckingball.wordlier.domain.ENTER

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "QWERTYUIOP"
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    onClick = onClick,
                    cellHeight = 32,
                    letterSize = 22,
                )
                if (index < keys.length - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "ASDFGHJKL"
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    onClick = onClick,
                    cellHeight = 32,
                    letterSize = 22,
                )
                if (index < keys.length - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "ZXCVBNM"
            KeyboardCharacterCell(
                letter = ENTER,
                onClick = onClick,
                cellHeight = 32,
                cellWidth = 42,
                letterSize = 11,
            )
            Spacer(modifier = Modifier.width(4.dp))
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    onClick = onClick,
                    cellHeight = 32,
                    letterSize = 22,
                )
                if (index < keys.length - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconCell(
                icon = painterResource(id = R.drawable.ic_delete),
                command = BACK,
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