package com.example.movieapp.db


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.*

private const val DATABASE_NAME = "movieDB"

class DBRepository private constructor(context: Context) {
    private val database: MovieDB = Room.databaseBuilder(
        context.applicationContext,
        MovieDB::class.java,
        DATABASE_NAME
    ).build()

    private val movieDao = database.movieDao()

    fun getRecent(): LiveData<List<Recent>> {
        var recent: LiveData<List<Recent>>
        runBlocking {
            recent = movieDao.getRecentList()
        }

        return recent
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addRecent(recent: Recent) {
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.addRecent(recent)
        }
    }

    companion object {
        private var INSTANCE: DBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DBRepository(context)
            }
        }

        fun get(): DBRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialize")
        }
    }


}