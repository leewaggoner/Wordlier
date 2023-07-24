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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.domain.BACK
import com.wreckingball.wordlier.domain.ENTER
import com.wreckingball.wordlier.domain.GameLetter
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell
import com.wreckingball.wordlier.ui.theme.WrongLetterCell
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

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
                    color = colors[key] ?: Color.White,
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
            val colors = findUsedLetters(keys, usedLetters)
            for ((index, key) in keys.withIndex()) {
                KeyboardCharacterCell(
                    letter = key.toString(),
                    color = colors[key] ?: Color.White,
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
            val colors = findUsedLetters(keys, usedLetters)
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
                    color = colors[key] ?: Color.White,
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

private object Comp {
    fun bestColor(a: Color, b: Color) : Color {
        val colorMap = mapOf(CorrectLetterCell to 3, WrongPositionCell to 2, WrongLetterCell to 1)
        val color1 = colorMap[a] ?: 1
        val color2 = colorMap[b] ?: 1
        return if (color1 > color2) a else b
    }
}

private fun  findUsedLetters(keyRow: String, usedLetters: List<GameLetter>) : Map<Char, Color> {
    //get list of all letters used in row
    val result = usedLetters.filter { letter ->
        letter.first in keyRow
    }
        //convert to map with duplicate colors
        .groupBy({ it.first }, { it.second })
        //filter the duplicate colors so only the best remains
        .mapValues { entry ->
            entry.value.reduce(Comp::bestColor)
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