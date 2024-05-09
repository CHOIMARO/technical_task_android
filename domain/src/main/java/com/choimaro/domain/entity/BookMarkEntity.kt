package com.choimaro.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.choimaro.domain.model.SearchListType

@Entity(tableName = "book_mark")
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = true)
    var idx: Long = 0,
    @ColumnInfo(name = "id")
    var id: String?,
    @ColumnInfo(name = "thumb_nail")
    var thumbnailUrl: String?,
    @ColumnInfo(name = "image_url")
    var imageUrl: String?,
    @ColumnInfo(name = "display_site_name")
    var displaySiteName: String?,
    @ColumnInfo(name = "date_time")
    var datetime: String?,
    @ColumnInfo(name = "item_type")
    var itemType: SearchListType?,
    @ColumnInfo(name = "doc_url")
    var docUrl: String?
)