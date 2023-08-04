package com.wreckingball.wordlier.utils

import android.text.format.DateUtils
import java.util.Date

fun Date.isYesterday() = DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)

