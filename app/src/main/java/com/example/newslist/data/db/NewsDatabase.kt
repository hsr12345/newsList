package com.example.newslist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [News::class], version = 1)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun newsDao(): NewsDao
    companion object{
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase?{
            if(INSTANCE == null){
                synchronized(NewsDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsDatabase::class.java,"contact.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE
        }

    }

}