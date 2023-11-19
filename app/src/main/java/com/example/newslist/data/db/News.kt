package com.example.newslist.data.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    var title: String,
    var url: String,
    var urlToImage: Bitmap? = null,
    var publishedAt: String,
    var isSelect: Boolean
)