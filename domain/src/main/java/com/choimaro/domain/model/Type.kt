package com.choimaro.domain.model

enum class SearchListType(typeName: String) {
    IMAGE("image"),
    WEB("web"),
    VIDEO("video"),
    BLOG("blog"),
    BOOK("book"),
    CAFE("cafe")
}

enum class SortType(val typeName: String) {
    ACCURACY("accuracy"),
    RECENCY("recency")
}