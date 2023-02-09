package com.hang.android.krhangman.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.db.Recent
import com.example.movieapp.db.RecentDao

@Database(entities = [Recent::class], version = 1)

abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): RecentDao
}