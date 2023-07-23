package com.wreckingball.wordlier.ui.compose

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
    flip: Boolean = false,
    onFlipFinished: (() -> Unit),
    onClick: ((String) -> Unit),
) {
    //animate the letter flip and color change
    val size = remember { Animatable(initialValue = 1.0f) }
    val guessColor = remember { Animatable(initialValue = color) }
    if (flip) {
        LaunchedEffect(key1 = letter) {
            guessColor.animateTo(
                targetValue = color,
                animationSpec = snap(delayMillis = 300)
            )
        }
        LaunchedEffect(key1 = letter) {
            val result = size.animateTo(
                targetValue = 1.0f,
                animationSpec = keyframes {
                    durationMillis = 600
                    0.0f at 300 with LinearEasing
                    1.0f at 600 with LinearEasing
                },
            )
            if (result.endReason == AnimationEndReason.Finished) {
                onFlipFinished()
            }
        }
    }

    Box(
        modifier = modifier.then(
            Modifier
                .graphicsLayer {
                    scaleY = size.value
                }
        )
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
                        color = guessColor.value,
                    )
                    .clickable {
                        onClick(letter)
                    }
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
}

@Preview
@Composable
fun CharacterCellPreview() {
    CharacterCell(
        letter = "W",
        color = NormalCell,
        onFlipFinished = { },
        onClick = { }
    )
}