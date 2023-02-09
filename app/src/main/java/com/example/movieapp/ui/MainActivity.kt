package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.api.MovieApiFetcher
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.btnSearch.setOnClickListener {
            MovieApiFetcher().getMovieList(mBinding.editSearch.text.toString())
        }
        setContentView(mBinding.root)
    }
}