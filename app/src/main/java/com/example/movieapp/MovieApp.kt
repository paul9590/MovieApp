package com.example.movieapp

import android.app.Application
import com.example.movieapp.api.MovieApiFetcher

class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()

        MovieApiFetcher.initialize()
    }
}