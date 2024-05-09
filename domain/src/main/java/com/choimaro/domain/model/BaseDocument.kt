package com.choimaro.domain.model

abstract class BaseDocument {
    abstract val id: String // 고유 아이디
    abstract val isCheckedBookMark: Boolean // 북마크 표시 유무
}
