package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.BACK
import com.wreckingball.wordlier.domain.ENTER
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    usedLetters: List<GameLetter>,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "QWERTYUIOP"
            val colors = findUsedLetters(keys, usedLetters)
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    color = colors[key] ?: Color.LightGray,
                    onClick = onClick,
                )
                if (index < keys.length - 1) {
                    Spacer(
                        modifier = Modifier.width(
                            MaterialTheme.dimensions.KeyboardTextPaddingDims
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.KeyboardRowPaddingDims)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "ASDFGHJKL"
            val colors = findUsedLetters(keys, usedLetters)
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    color = colors[key] ?: Color.LightGray,
                    onClick = onClick,
                )
                if (index < keys.length - 1) {
                    Spacer(
                        modifier = Modifier.width(
                            MaterialTheme.dimensions.KeyboardTextPaddingDims
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.KeyboardRowPaddingDims)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            val keys = "ZXCVBNM"
            val colors = findUsedLetters(keys, usedLetters)
            KeyboardCharacterCell(
                letter = ENTER,
                onClick = onClick,
                cellWidth = MaterialTheme.dimensions.ActionBoxWidth,
                letterSize = MaterialTheme.dimensions.EnterTextSize,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.KeyboardTextPaddingDims))
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    color = colors[key] ?: Color.LightGray,
                    onClick = onClick,
                )
                if (index < keys.length - 1) {
                    Spacer(
                        modifier = Modifier.width(
                            MaterialTheme.dimensions.KeyboardTextPaddingDims
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.KeyboardTextPaddingDims))
            IconCell(
                icon = painterResource(id = R.drawable.ic_delete),
                command = BACK,
                onClick = onClick,
            )
        }
    }
}

private fun  findUsedLetters(keyRow: String, usedLetters: List<GameLetter>) : Map<Char, Color> {
    val colorMap = mapOf(CorrectLetterCell to 3, WrongPositionCell to 2, WrongLetterCell to 1)

    //get list of all letters used in row
    val result = usedLetters.filter { letter ->
        letter.letter in keyRow
    }
        //convert to map with duplicate colors
        .groupBy({ it.letter }, { it.color })
        //filter the duplicate colors so only the best remains
        .mapValues { entry ->
            entry.value.reduce { acc, color ->
                val color1 = colorMap[color] ?: 1
                val color2 = colorMap[acc] ?: 1
                if (color1 > color2) color else acc
            }
        }

    return result
}

@Preview
@Composable
fun KeyboardPreview() {
    Keyboard(
        usedLetters = listOf(),
        onClick = { },
    )
}