package com.example.news_app_jc.mvvm

import com.example.news_app_jc.api.NewsApi
import com.example.news_app_jc.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Repository @Inject constructor(private val newsApi: NewsApi){

    private val _newsList = MutableStateFlow<List<Article>>(emptyList())
    val newsList :StateFlow<List<Article>>
        get() = _newsList


    suspend fun getNews(category: String) {
        val result = newsApi.getNews(category)

        if (result.isSuccessful && result.body()!=null){
            _newsList.emit(result.body()!!.articles)
        }
    }

}