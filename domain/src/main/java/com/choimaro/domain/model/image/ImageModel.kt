package com.choimaro.domain.model.image

import android.os.Parcelable
import com.choimaro.domain.model.BaseDocument
import com.choimaro.domain.model.SearchListType
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.security.MessageDigest
import java.util.Date
import java.util.UUID

@Parcelize
data class ImageModel(
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val displaySiteName: String?,
    val datetime: String?,
    val itemType: SearchListType = SearchListType.IMAGE,
    val docUrl: String?,
    override var isCheckedBookMark: Boolean = false,
    override var id: String
) : Parcelable, BaseDocument()