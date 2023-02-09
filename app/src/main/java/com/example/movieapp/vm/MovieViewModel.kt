package com.example.movieapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.api.MovieApiFetcher
import com.example.movieapp.model.Movie

class MovieViewModel: ViewModel() {
    private val _movieList = MutableLiveData<ArrayList<Movie>>()
    val movieList: MutableLiveData<ArrayList<Movie>>
        get() = _movieList


    fun getMovieList(query: String) {
        MovieApiFetcher.get().getMovieList(_movieList, query)
    }
}