package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.recycler.MovieRecyclerAdapter
import com.example.movieapp.vm.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel = MovieViewModel()
    private val movieRecyclerAdapter = MovieRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            btnSearch.setOnClickListener {
                movieViewModel.getMovieList(editSearch.text.toString())
            }

            recyclerMovie.adapter = movieRecyclerAdapter
            recyclerMovie.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }


        movieViewModel.movieList.observe(this) {
            movieViewModel.movieList.value?.let { movieRecyclerAdapter.setData(it)}
        }
        setContentView(mBinding.root)
    }
}