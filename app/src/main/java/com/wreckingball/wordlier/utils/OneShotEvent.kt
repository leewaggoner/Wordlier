package com.wreckingball.wordlier.utils

import androidx.annotation.VisibleForTesting

data class OneShotEvent<T : Any>(@get:VisibleForTesting val value: T) {
    @get:Synchronized
    private var hasBeenConsumed = false

    fun consume(consumer: (T) -> Unit) {
        if (!hasBeenConsumed) {
            consumer(value)
            hasBeenConsumed = true
        }
    }

    fun consume(): T? {
        return if (hasBeenConsumed) {
            null
        } else {
            hasBeenConsumed = true
            value
        }
    }
}

fun <T : Any> T.toEvent() = OneShotEvent(this)