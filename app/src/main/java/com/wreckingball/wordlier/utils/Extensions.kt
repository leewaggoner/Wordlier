package com.wreckingball.wordlier.utils

import android.text.format.DateUtils
import com.wreckingball.wordlier.domain.StreakStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.isYesterday() = DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)

fun String.dateStreakResult() : StreakStatus {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return if (isNotEmpty()) {
        val lastDatePlayed = sdf.parse(this)
        if (lastDatePlayed != null) {
            // if the date last played is today, do nothing
            if (!DateUtils.isToday(lastDatePlayed.time)) {
                if (lastDatePlayed.isYesterday()) {
                    // last game played was yesterday, the streak continues!
                    StreakStatus.UNBROKEN
                } else {
                    // last game played was NOT yesterday, the streak is broken
                    StreakStatus.BROKEN
                }
            } else {
                // last game played was today -- return not broken for testing purposes
                StreakStatus.UNBROKEN
            }
        } else {
            // invalid date
            StreakStatus.BROKEN
        }
    } else {
        // empty date string, probably the first game ever played
        StreakStatus.FIRST_GAME
    }
}

