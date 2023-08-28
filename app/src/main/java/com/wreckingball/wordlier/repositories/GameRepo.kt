package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.network.NetworkResponse
import com.wreckingball.wordlier.network.WordValidationService
import com.wreckingball.wordlier.utils.wordlierDateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Date

class GameRepo(private val wordValidationService: WordValidationService) {
    private val words = mutableMapOf<String, String>()
    fun initWords() {
        words["2023-08-28"] = "FAULT"
        words["2023-08-29"] = "ADULT"
        words["2023-08-30"] = "GRANT"
        words["2023-08-31"] = "EARTH"
        words["2023-09-01"] = "CRIME"
        words["2023-09-02"] = "ROBIN"
        words["2023-09-03"] = "BIRTH"
        words["2023-09-04"] = "STALE"
        words["2023-09-05"] = "ANGER"
        words["2023-09-06"] = "DERBY"
        words["2023-09-07"] = "FLANK"
        words["2023-09-08"] = "GHOST"
        words["2023-09-09"] = "WINCH"
        words["2023-09-10"] = "AGENT"
        words["2023-09-11"] = "BLOCK"
        words["2023-09-12"] = "CHAIN"
        words["2023-09-13"] = "DEPTH"
        words["2023-09-14"] = "TAINT"
        words["2023-09-15"] = "LLAMA"
    }

    fun getDailyWord() : String {
        return words[Date().wordlierDateToString()] ?: "TRUST"
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
