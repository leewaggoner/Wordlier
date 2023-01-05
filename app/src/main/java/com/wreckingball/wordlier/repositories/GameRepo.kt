package com.wreckingball.wordlier.repositories

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GameRepo {
    private val words = mutableMapOf<String, String>()
    fun initWords() {
        words["2023-01-04"] = "ADULT"
        words["2023-01-05"] = "GRANT"
        words["2023-01-06"] = "EARTH"
        words["2023-01-07"] = "CRIME"
        words["2023-01-08"] = "ROBIN"
        words["2023-01-09"] = "BIRTH"
        words["2023-01-10"] = "STALE"
        words["2023-01-11"] = "ANGER"
        words["2023-01-12"] = "DERBY"
        words["2023-01-13"] = "FLANK"
        words["2023-01-14"] = "GHOST"
        words["2023-01-15"] = "WINCH"
        words["2023-01-16"] = "AGENT"
        words["2023-01-17"] = "BLOCK"
        words["2023-01-18"] = "CHAIN"
        words["2023-01-19"] = "DEPTH"
        words["2023-01-20"] = "FAULT"
    }

    fun getDailyWord() : String {
        val today = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return words[sdf.format(today)] ?: "TRUST"
    }
}