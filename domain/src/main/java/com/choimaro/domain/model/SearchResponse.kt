package com.choimaro.domain.model

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    @SerializedName("meta")
    val metaData: MetaData?,
    @SerializedName("documents")
    var documents: MutableList<T>?,
)
