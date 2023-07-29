package com.example.news_app_jc.api

import com.example.news_app_jc.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&apiKey=e7615d1c8e0341a784b178a2cdc7e3ce

private const val api_key = "e7615d1c8e0341a784b178a2cdc7e3ce"

interface NewsApi {

    @GET("top-headlines?country=in&apiKey=$api_key")
    suspend fun getNews( @Query("category")category:String): Response<News>

}