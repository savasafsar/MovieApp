package com.example.moviecatch.di.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviecatch.model.Genre

@Database(entities = [GenreData::class ], version = 1, exportSchema = false)
abstract class GenreDatabase :RoomDatabase(){
    abstract fun getDAO() : GenreDao
    companion object{
        private var dbInstance : GenreDatabase?=null
       fun getAppDB(context:Context) : GenreDatabase{
           if (dbInstance == null) {
        dbInstance = Room.databaseBuilder<GenreDatabase>(context.applicationContext,GenreDatabase::class.java,
            "genre_database")
            .allowMainThreadQueries()
            .build()
           }
           return dbInstance!!
       }
    }
}
