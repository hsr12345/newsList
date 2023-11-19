package com.example.newslist.data.dto

data class Articles(
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    var isSelect: Boolean
)