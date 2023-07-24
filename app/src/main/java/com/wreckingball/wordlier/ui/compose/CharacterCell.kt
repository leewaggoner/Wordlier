package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.wreckingball.wordlier.ui.theme.NormalCell
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun CharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    cellHeight: Dp = MaterialTheme.dimensions.GuessLetterBoxDims,
    cellWidth: Dp = cellHeight,
    letterSize: TextUnit = MaterialTheme.dimensions.GuessTextSize,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(cellWidth, cellHeight)
                .border(
                    width = MaterialTheme.dimensions.LetterBoxBorderDims,
                    color = Color.Black,
                    shape = RoundedCornerShape(MaterialTheme.dimensions.LetterBoxCornerDims)
                )
                .clip(RoundedCornerShape(MaterialTheme.dimensions.LetterBoxCornerDims))
                .background(
                color = color
                )
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = letterSize,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun CharacterCellPreview() {
    CharacterCell(
        letter = "W",
        color = NormalCell,
    )
}