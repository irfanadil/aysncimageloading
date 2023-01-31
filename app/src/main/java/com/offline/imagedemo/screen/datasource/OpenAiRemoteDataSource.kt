package com.offline.imagedemo.screen.datasource

import com.cute.connection.api.FailureStatus
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.ServerApi
import com.offline.imagedemo.screen.model.ResponseModel
import com.offline.imagedemo.screen.model.RequestModel
import javax.inject.Inject

class OpenAiRemoteDataSource  @Inject constructor(private val serverApi: ServerApi) {

    suspend fun fetchImageUrl(imageDetail:String): GenericApiResponse<ResponseModel> {
        return try {
            val newToken = "Bearer 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" // it can be kept in the local.gradle file...
            val response = serverApi.getImageUrls(newToken , RequestModel(1 , imageDetail, "1024x1024"))
            GenericApiResponse.Success(response)
        } catch (e: Throwable) {
            GenericApiResponse.Failure(FailureStatus.API_FAIL, 60,e.message)
        }
    }
}