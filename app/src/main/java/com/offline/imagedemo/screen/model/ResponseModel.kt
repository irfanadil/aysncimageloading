package com.offline.imagedemo.screen.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    val created: Long,
    @SerializedName("data") val apiImageUrl: List<ApiImageUrl>,
)

data class ApiImageUrl(val url: String)

