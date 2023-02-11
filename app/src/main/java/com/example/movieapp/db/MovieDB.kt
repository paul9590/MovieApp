package com.example.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recent::class], version = 1)

abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): RecentDao
}