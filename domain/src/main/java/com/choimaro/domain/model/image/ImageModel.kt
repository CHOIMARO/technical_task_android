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
    val itemType: SearchListType,
    val docUrl: String?,
    override val isBookMark: Boolean = false
) : Parcelable, BaseDocument() {
    @IgnoredOnParcel
    override val id: String = generateHash(imageUrl + docUrl) // 고유 아이디
    override fun generateHash(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}