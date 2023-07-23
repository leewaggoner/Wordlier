package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.ui.theme.WrongPositionCell

@Composable
fun KeyboardCharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    cellHeight: Int = 50,
    cellWidth: Int = cellHeight,
    letterSize: Int = 36,
    onClick: ((String) -> Unit),
) {
    Box(
        modifier = modifier.then(
            Modifier
                .clickable { onClick(letter) }
        )
    ) {
        CharacterCell(
            letter = letter,
            modifier = modifier,
            color = color,
            cellHeight = cellHeight,
            cellWidth = cellWidth,
            letterSize = letterSize,
        )
    }
}

@Preview
@Composable
fun KeyboardCharacterCellPreview() {
    KeyboardCharacterCell(
        letter = "W",
        color = WrongPositionCell,
        onClick = { }
    )
}
