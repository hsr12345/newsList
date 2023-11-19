package com.example.newslist.data.net

import com.example.newslist.data.dto.Articles
import com.example.newslist.data.dto.NewsListRes
import com.example.newslist.data.net.ReqresService
import javax.inject.Inject

class NewsCase @Inject constructor(private val reqresService: ReqresService) {
    suspend fun getNewsList(country: String, apiKey: String): List<Articles>? {
        return reqresService.getNewsList(country, apiKey).body()?.articles
    }

    suspend fun getNewsData(country: String, apiKey: String) : NewsListRes? {
        return reqresService.getNewsList(country, apiKey).body()
    }
}