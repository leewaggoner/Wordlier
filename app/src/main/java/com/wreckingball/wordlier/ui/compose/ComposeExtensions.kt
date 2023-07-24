package com.wreckingball.wordlier.ui.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo

private const val SHAKE_DURATION = 50

fun Modifier.shake(enabled: Boolean, onAnimationFinished: () -> Unit) = composed(
    factory = {
        val distance by animateFloatAsState(
            targetValue = if (enabled) 15f else 0f,
            animationSpec = repeatable(
                iterations = 8,
                animation = tween(durationMillis = SHAKE_DURATION, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            finishedListener = { onAnimationFinished() }
        )
        Modifier.graphicsLayer {
            translationX = if (enabled) distance else 0f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)
