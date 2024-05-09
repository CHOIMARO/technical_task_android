package com.choimaro.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errorType")
    var errorType: String?,
    @SerializedName("message")
    var message: String?
)
