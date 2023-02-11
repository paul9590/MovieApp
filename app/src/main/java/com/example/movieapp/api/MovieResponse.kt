package com.example.movieapp.api

import com.example.movieapp.model.Movie

data class MovieResponse(
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: List<Movie>
)