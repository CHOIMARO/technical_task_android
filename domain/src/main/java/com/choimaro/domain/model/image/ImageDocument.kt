package com.choimaro.domain.model.image

import com.choimaro.domain.model.BaseDocument
import com.google.gson.annotations.SerializedName
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date

data class ImageDocument(
    @SerializedName("collection")
    val collection: String?, // 컬렉션
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?, // 미리보기 이미지 URL
    @SerializedName("image_url")
    val imageUrl: String?, // 이미지 URL
    @SerializedName("width")
    val width: Int?, // 이미지의 가로 길이
    @SerializedName("height")
    val height: Int?, // 이미지의 세로 길이
    @SerializedName("display_sitename")
    val displaySiteName: String?, // 출처
    @SerializedName("doc_url")
    val docUrl: String?, // 문서 URL
    @SerializedName("datetime")
    val dateTime: Date?, // 문서 작성시간
)
