package com.example.moviecatch.di.dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteDataClass::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}