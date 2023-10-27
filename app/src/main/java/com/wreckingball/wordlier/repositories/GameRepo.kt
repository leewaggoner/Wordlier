package com.wreckingball.wordlier.repositories

import com.wreckingball.wordlier.network.NetworkResponse
import com.wreckingball.wordlier.network.WordValidationService
import com.wreckingball.wordlier.utils.DataStoreWrapper
import com.wreckingball.wordlier.utils.isEarlierThanToday
import com.wreckingball.wordlier.utils.wordlierDateFromString
import com.wreckingball.wordlier.utils.wordlierDateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Date

class GameRepo(
    private val wordValidationService: WordValidationService,
    private val dataStore: DataStoreWrapper
) {
    private val words = mutableMapOf<String, String>()
    fun initWords() {
        words["2023-10-26"] = "FAULT"
        words["2023-10-27"] = "ADULT"
        words["2023-10-28"] = "GRANT"
        words["2023-10-29"] = "EARTH"
        words["2023-10-30"] = "QUACK"
        words["2023-10-31"] = "JUMPY"
        words["2023-11-01"] = "CRIME"
        words["2023-11-02"] = "ROBIN"
        words["2023-11-03"] = "BIRTH"
        words["2023-11-04"] = "STALE"
        words["2023-11-05"] = "ANGER"
        words["2023-11-06"] = "DERBY"
        words["2023-11-07"] = "FLANK"
        words["2023-11-08"] = "GHOST"
        words["2023-11-09"] = "WINCH"
        words["2023-11-10"] = "AGENT"
        words["2023-11-11"] = "BLOCK"
        words["2023-11-12"] = "CHAIN"
        words["2023-11-13"] = "DEPTH"
        words["2023-11-14"] = "TAINT"
        words["2023-11-15"] = "BUXOM"
        words["2023-11-16"] = "ZILCH"
        words["2023-11-17"] = "BOOZY"
        words["2023-11-18"] = "JIMMY"
        words["2023-11-19"] = "BACON"
        words["2023-11-20"] = "CADET"
        words["2023-11-21"] = "FACET"
        words["2023-11-22"] = "LAMED"
        words["2023-11-23"] = "JAZZY"
    }

    suspend fun isNewPuzzleReady() = withContext(Dispatchers.IO) {
        //if the last date played is yesterday or earlier, a new puzzle is ready
        val lastDatePlayed = dataStore.getLastDatePlayed("").wordlierDateFromString()
        //if last date played is null, it's the first game ever played
        lastDatePlayed == null || lastDatePlayed.isEarlierThanToday()
    }

    fun getDailyWord() : String {
        return words[Date().wordlierDateToString()] ?: "TRUST"
    }

    suspend fun validateWord(word: String) = withContext(Dispatchers.IO) {
        when (val response = callDictionaryApi(word)) {
            is NetworkResponse.Success -> {
                response.data[0].word.isNotEmpty()
            }
            is NetworkResponse.Error -> {
                //return true for anything other than 404-Not Found (invalid word). If the server is
                //failing the game shouldn't stop
                response.code != 404
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

private fun HttpException.toNetworkErrorResponse(): NetworkResponse<Nothing> =
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
