package com.choimaro.domain

import com.choimaro.domain.model.SearchResponse

sealed class ResponseState {
    object Loading : ResponseState()
    data class Success<T>(val data: T) : ResponseState()
    data class Fail(val exception: String = Exception().message ?: "") : ResponseState()
    object Init : ResponseState()

    companion object {
        fun init(): ResponseState = Init
    }
}
