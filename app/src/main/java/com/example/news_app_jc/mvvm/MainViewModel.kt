package com.example.news_app_jc.mvvm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app_jc.model.Article
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository) : ViewModel() {

    lateinit var newsList:StateFlow<List<Article>>

    fun getNews(category:String){
        viewModelScope.launch {
            repository.getNews(category)
        }
        newsList = repository.newsList
    }

}