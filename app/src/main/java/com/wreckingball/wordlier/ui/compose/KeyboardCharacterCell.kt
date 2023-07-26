package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.wreckingball.wordlier.ui.theme.WrongPositionCell
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun KeyboardCharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    cellHeight: Dp = MaterialTheme.dimensions.KeyboardLetterBoxDims,
    cellWidth: Dp = cellHeight,
    letterSize: TextUnit = MaterialTheme.dimensions.KeyboardTextSize,
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
