package com.example.movieapp

import android.app.Application
import com.example.movieapp.api.MovieApiFetcher
import com.hang.android.krhangman.db.DBRepository

class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
        DBRepository.initialize(this)
        MovieApiFetcher.initialize()
    }
}