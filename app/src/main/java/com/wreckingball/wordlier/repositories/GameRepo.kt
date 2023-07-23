package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.network.NetworkResponse
import com.wreckingball.wordlier.network.WordValidationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameRepo(private val wordValidationService: WordValidationService) {
    private val words = mutableMapOf<String, String>()
    fun initWords() {
        words["2023-07-23"] = "FAULT"
        words["2023-07-24"] = "ADULT"
        words["2023-07-25"] = "GRANT"
        words["2023-07-26"] = "EARTH"
        words["2023-07-27"] = "CRIME"
        words["2023-07-28"] = "ROBIN"
        words["2023-07-29"] = "BIRTH"
        words["2023-07-30"] = "STALE"
        words["2023-07-13"] = "ANGER"
        words["2023-08-01"] = "DERBY"
        words["2023-08-02"] = "FLANK"
        words["2023-08-03"] = "GHOST"
        words["2023-08-04"] = "WINCH"
        words["2023-08-05"] = "AGENT"
        words["2023-08-06"] = "BLOCK"
        words["2023-08-07"] = "CHAIN"
        words["2023-08-08"] = "DEPTH"
    }

    fun getDailyWord() : String {
        val today = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return words[sdf.format(today)] ?: "TRUST"
    }

    suspend fun validateWord(word: String): Boolean {
        return when (val response = callDictionaryApi(word)) {
            is NetworkResponse.Success -> {
                response.data[0].word.isNotEmpty()
            }
            is NetworkResponse.Error -> {
                //return true for anything other than 404-Not Found (invalid word). If the server is
                //failing the game shouldn't stop
                return response.code != 404
            }
        }
    }

    private suspend fun callDictionaryApi(word: String) = withContext(Dispatchers.IO) {
            try {
                NetworkResponse.Success(wordValidationService.validateWord(word))
            } catch (ex: HttpException) {
                ex.toNetworkErrorResponse()
            } catch (ex: Exception) {
                NetworkResponse.Error.UnknownNetworkError(ex)
            }
        }
    }

fun HttpException.toNetworkErrorResponse(): NetworkResponse<Nothing> =
    when (val code = code()) {
        400 -> NetworkResponse.Error.BadRequest(this, code)
        401,
        403 -> NetworkResponse.Error.Unauthorized(this, code)
        404 -> NetworkResponse.Error.NotFound(this, code)
        429 -> NetworkResponse.Error.TooManyRequests(this, code)
        in 400..499 -> NetworkResponse.Error.ApiError(this, code)
        in 500..599 -> NetworkResponse.Error.ServerError(this, code)
        else -> NetworkResponse.Error.UnknownNetworkError(this, code)
    }
