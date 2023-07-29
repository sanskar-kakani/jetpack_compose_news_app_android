package com.example.news_app_jc.model

data class Article(
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val source: Source
)