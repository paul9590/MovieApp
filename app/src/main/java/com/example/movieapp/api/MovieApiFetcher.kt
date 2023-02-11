package com.example.movieapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val NAVER_CLIENT = BuildConfig.NAVER_CLIENT_ID
const val NAVER_KEY = BuildConfig.NAVER_KEY

class MovieApiFetcher {
    private val movieApi: MovieApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApi = retrofit.create(MovieApi::class.java)
    }

    fun getMovieList(movieList: MutableLiveData<ArrayList<Movie>>, query: String, start: Int = 1) {
        val service = movieApi.getSearchResult(NAVER_CLIENT, NAVER_KEY, query, start)
        service.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    Log.e("receive", response.body()!!.toString())
                    if(response.body()!!.total < start) return
                    movieList.postValue(response.body()!!.items as ArrayList<Movie>)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })
    }

    companion object {
        private var INSTANCE: MovieApiFetcher? = null
        fun initialize() {
            if(INSTANCE == null) {
                INSTANCE = MovieApiFetcher()
            }
        }
        fun get(): MovieApiFetcher {
            return INSTANCE ?: throw IllegalStateException("Retrofit must be initialize")
        }
    }
}