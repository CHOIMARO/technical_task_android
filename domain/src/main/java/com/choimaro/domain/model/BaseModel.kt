package com.choimaro.domain.model

sealed class BaseModel {
    abstract val id: String // 고유 아이디
    abstract val isCheckedBookMark: Boolean // 북마크 표시 유무
}
