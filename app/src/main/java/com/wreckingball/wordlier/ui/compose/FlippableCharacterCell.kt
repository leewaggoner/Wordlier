package com.wreckingball.wordlier.ui.compose

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.ui.theme.CorrectLetterCell

@Composable
fun FlippableCharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    flip: Boolean = false,
    onFlipFinished: (() -> Unit),
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
        CharacterCell(
            letter = letter,
            modifier = modifier,
            color = guessColor.value,
        )
    }
}

@Preview
@Composable
fun FlippableCharacterCellPreview() {
    FlippableCharacterCell(
        letter = "W",
        color = CorrectLetterCell,
        onFlipFinished = { }
    )
}