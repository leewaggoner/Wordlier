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

@Composable
fun CharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(50.dp, 50.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = color,
                ),
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun CharacterCellPreview() {
    CharacterCell(
        letter = "W",
        color = Color.Green
    )
}