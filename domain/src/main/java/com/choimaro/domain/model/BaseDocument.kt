package com.choimaro.domain.model

abstract class BaseDocument {
    abstract val id: String // 고유 아이디
    abstract val isBookMark: Boolean // 북마크 표시 유무
    abstract fun generateHash(input: String): String // 문서의 고유 ID
}
