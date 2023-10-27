package com.wreckingball.wordlier.utils

import android.text.format.DateUtils
import com.wreckingball.wordlier.domain.StreakStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.isYesterday() = DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)

fun Date.isEarlierThanToday(): Boolean {
    val todayStr = Date().wordlierDateToString()
    val today = todayStr.wordlierDateFromString()
    return this.before(today)
}

fun String.dateStreakResult() : StreakStatus {
    return if (isNotEmpty()) {
        val lastDatePlayed = this.wordlierDateFromString()
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

fun Date.wordlierDateToString() : String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

fun String.wordlierDateFromString() : Date? {
    return if (this.isEmpty()) {
        null
    } else {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.parse(this)
    }
}

fun Date.introDateToString() : String {
    val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}

