package com.example.newslist.data.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel(application: Application) : ViewModel() {

    private val repository = NewsRepository(application)

    fun getAll(): LiveData<List<News?>> {
        return repository.getAll()
    }

    fun insert(news: News){
        repository.insert(news)
    }

    fun update(news: News){
        repository.update(news)
    }

    fun insertAll(news: List<News?>){
        repository.insertAll(news)
    }

    override fun onCleared() {
        super.onCleared()
    }
}