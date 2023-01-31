package com.cute.connection.api

import com.offline.imagedemo.screen.model.ResponseModel


sealed class GenericApiResponse<out T> {

    class Success<out T>(val value: T) : GenericApiResponse<T>()

    class Failure(
        val failureStatus: FailureStatus = FailureStatus.API_FAIL,
        val code: Int? = null,
        val message: String? = null
    ) : GenericApiResponse<Nothing>()

}

enum class FailureStatus {
    EMPTY,
    API_FAIL,
    NO_INTERNET,
    OTHER
}


sealed class UrlViewState {
    object Empty : UrlViewState()
    object Loading : UrlViewState()
    data class Success(val responseModel: ResponseModel) : UrlViewState()
    data class Error(val exception: String) : UrlViewState()
}






