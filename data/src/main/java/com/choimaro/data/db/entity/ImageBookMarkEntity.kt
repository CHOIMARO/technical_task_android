package com.choimaro.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.choimaro.domain.model.ImageModel

@Entity(tableName = "book_mark")
data class ImageBookMarkEntity(
    @PrimaryKey(autoGenerate = true)
    var idx: Long = 0,
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "thumb_nail")
    var thumbnailUrl: String,
    @ColumnInfo(name = "image_url")
    var imageUrl: String,
    @ColumnInfo(name = "display_site_name")
    var displaySiteName: String,
    @ColumnInfo(name = "date_time")
    var datetime: String,
    @ColumnInfo(name = "doc_url")
    var docUrl: String
)

fun ImageBookMarkEntity.toDomainModel(): ImageModel {
    return ImageModel(
        id = id,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySiteName = displaySiteName,
        datetime = datetime,
        docUrl = docUrl,
        isCheckedBookMark = true
    )
}

fun ImageModel.toBookMarkEntity(): ImageBookMarkEntity {
    return ImageBookMarkEntity(
        id = id,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySiteName = displaySiteName,
        datetime = datetime,
        docUrl = docUrl
    )
}