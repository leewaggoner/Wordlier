package com.wreckingball.wordlier.ui.compose

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.EaseOutBounce
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

private const val FLIP_ANIM_DURATION = 500
private const val WAVE_ANIM_DURATION = 200

@Composable
fun FlippableCharacterCell(
    letter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    wave: Boolean,
    onWaveFinished: () -> Unit,
    flip: Boolean,
    onFlipFinished: () -> Unit,
) {
    //animate the letter flip and color change
    val size = remember { Animatable(initialValue = 1.0f) }
    val guessColor = remember { Animatable(initialValue = color) }
    if (flip) {
        LaunchedEffect(key1 = letter) {
            guessColor.animateTo(
                targetValue = color,
                animationSpec = snap(delayMillis = FLIP_ANIM_DURATION / 2)
            )
        }
        LaunchedEffect(key1 = letter) {
            val result = size.animateTo(
                targetValue = 1.0f,
                animationSpec = keyframes {
                    durationMillis = FLIP_ANIM_DURATION
                    0.0f at FLIP_ANIM_DURATION / 2 with LinearEasing
                    1.0f at FLIP_ANIM_DURATION with LinearEasing
                },
            )
            if (result.endReason == AnimationEndReason.Finished) {
                onFlipFinished()
            }
        }
    }

    //do a wave
    val height = remember { Animatable(initialValue = 0.0f) }
    if (wave) {
        LaunchedEffect(key1 = letter) {
            val result = height.animateTo(
                targetValue = 0.0f,
                animationSpec = keyframes {
                    durationMillis = WAVE_ANIM_DURATION
                    -25.0f at WAVE_ANIM_DURATION / 4 with LinearEasing
                    25.0f at WAVE_ANIM_DURATION with EaseOutBounce
                }
            )
            if (result.endReason == AnimationEndReason.Finished) {
                onWaveFinished()
            }
        }
    }

    Box(
        modifier = modifier.then(
            Modifier
                .graphicsLayer {
                    scaleY = size.value
                    translationY = height.value
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
        wave = false,
        onWaveFinished = { },
        flip = false,
        onFlipFinished = { },
    )
}