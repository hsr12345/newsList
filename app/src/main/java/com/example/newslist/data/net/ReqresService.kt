package com.example.newslist.data.net

import com.example.newslist.data.dto.NewsListRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("/v2/top-headlines")
    suspend fun getNewsList(@Query("country") country:String, @Query("apiKey") apiKey:String) : Response<NewsListRes>

}