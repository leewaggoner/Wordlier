package com.wreckingball.wordlier.network

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()

    sealed class Error(open val exception: Exception, open val code: Int) : NetworkResponse<Nothing>() {
        data class TooManyRequests(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class NotFound(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class Unauthorized(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class BadRequest(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class ApiError(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class ServerError(
            override val exception: Exception,
            override val code: Int,
        ) : Error(exception, code)

        data class UnknownNetworkError(
            override val exception: Exception,
            override val code: Int = 0,
        ) : Error(exception, code)
    }
}