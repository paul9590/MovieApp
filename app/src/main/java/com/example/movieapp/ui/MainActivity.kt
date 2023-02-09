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
            recyclerMovie.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(recyclerView.canScrollVertically(-1)) {
                        movieViewModel.getMoreMovieList(movieRecyclerAdapter.itemCount)
                    }
                }
            })
        }


        movieViewModel.movieList.observe(this) {
            movieViewModel.movieList.value?.let { movieRecyclerAdapter.setData(it)}
        }

        movieViewModel.moreMovieList.observe(this) {
            movieViewModel.moreMovieList.value?.let { movieRecyclerAdapter.addData(it)}
        }

        setContentView(mBinding.root)
    }
}