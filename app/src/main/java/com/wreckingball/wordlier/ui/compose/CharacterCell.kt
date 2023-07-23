package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wreckingball.wordlier.ui.theme.NormalCell

@Composable
fun CharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    cellHeight: Int = 50,
    cellWidth: Int = cellHeight,
    letterSize: Int = 36,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(cellWidth.dp, cellHeight.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(
                color = color
                )
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = letterSize.sp,
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