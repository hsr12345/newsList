package com.example.newslist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newslist.data.dto.Articles
import com.example.newslist.data.dto.NewsListRes
import com.example.newslist.data.net.NewsCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject internal constructor(private val newsCase: NewsCase): ViewModel(){

    private val _newsList = MutableLiveData<List<Articles>>()//MutableStateFlow<List<Articles>>(listOf())
    val newsList = _newsList

    private val _data = MutableLiveData<NewsListRes>()
    val data = _data

    init {
//        readNewsList()
//        readData()
    }

    fun readNewsList(){
        viewModelScope.launch {
//            _newsList.value = newsCase.getNewsList("kr","c2914225a1e047cab70a286a7acefcf8")!!
            _newsList.postValue(newsCase.getNewsList("kr","c2914225a1e047cab70a286a7acefcf8"))
        }
    }

    fun readData(){
        viewModelScope.launch {
            _data.postValue(newsCase.getNewsData("kr","c2914225a1e047cab70a286a7acefcf8"))
        }
    }
}