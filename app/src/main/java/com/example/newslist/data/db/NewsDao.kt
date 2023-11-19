package com.example.newslist.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newslist.data.dto.Articles

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY id ASC")
    fun getAll() : LiveData<List<News?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News?)

    @Update
    fun update(news: News?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news : List<News?>)
}