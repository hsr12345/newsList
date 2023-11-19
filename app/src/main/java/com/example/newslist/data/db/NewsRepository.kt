package com.example.newslist.data.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData

class NewsRepository(application: Application) {
    private val newsDatabase = NewsDatabase.getInstance(application)!!
    private val newsDao: NewsDao = newsDatabase.newsDao()
    private val newsList: LiveData<List<News?>> = newsDao.getAll()

    fun getAll(): LiveData<List<News?>>{
        return newsList
    }

    fun insert(news: News?){
        try {
            val thread = Thread {
                newsDao.insert(news)
            }
            thread.start()
        }catch (e: Exception){

        }
    }

    fun insertAll(news: List<News?>){
        try {
            val thread = Thread {
                newsDao.insertAll(news)
            }
            thread.start()
        }catch (e: Exception){

        }
    }

    fun update(news: News?){
        try {
            val thread = Thread {
                newsDao.update(news)
            }
            thread.start()
        }catch (e: Exception){

        }
    }
}