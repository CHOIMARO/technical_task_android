package com.choimaro.domain.model

data class ImageModel(
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySiteName: String,
    val datetime: String,
    val docUrl: String,
    override var isCheckedBookMark: Boolean = false,
    override var id: String
): BaseModel()