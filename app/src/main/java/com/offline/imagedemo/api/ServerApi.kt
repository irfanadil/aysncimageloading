package com.cute.connection.api

import com.offline.imagedemo.screen.model.ResponseModel
import com.offline.imagedemo.screen.model.RequestModel
import retrofit2.http.*

interface ServerApi {

    @POST("v1/images/generations")
    suspend fun getImageUrls(@Header("Authorization")  Authorization:String, @Body data: RequestModel): ResponseModel

}


