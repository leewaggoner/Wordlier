package com.wreckingball.wordlier.network

import com.wreckingball.wordlier.models.DictionaryEntry
import retrofit2.http.GET
import retrofit2.http.Path

interface WordValidationService {
    @GET("v2/entries/en/{word}")
    suspend fun validateWord(@Path("word") word: String) : DictionaryEntry
}