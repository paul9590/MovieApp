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
        var calling = false
        val mBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            btnSearch.setOnClickListener {
                calling = true
                movieRecyclerAdapter.resetData()
                movieViewModel.getMovieList(editSearch.text.toString())
            }

            recyclerMovie.adapter = movieRecyclerAdapter
            recyclerMovie.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            recyclerMovie.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(recyclerView.canScrollVertically(-1)) {
                        if(calling) return
                        calling = true
                        movieViewModel.getMoreMovieList(movieRecyclerAdapter.itemCount + 1)
                    }
                }
            })
        }


        movieViewModel.movieList.observe(this) {
            calling = false
            movieViewModel.movieList.value?.let { movieRecyclerAdapter.setData(it)}
        }

        movieViewModel.moreMovieList.observe(this) {
            calling = false
            movieViewModel.moreMovieList.value?.let { movieRecyclerAdapter.addData(it)}
        }

        setContentView(mBinding.root)
    }
}