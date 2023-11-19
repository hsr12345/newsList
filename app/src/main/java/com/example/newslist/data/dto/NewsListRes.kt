package com.example.newslist.data.dto

data class NewsListRes(
    val status : String,
    val totalResults : Int,
    val articles : List<Articles>
)