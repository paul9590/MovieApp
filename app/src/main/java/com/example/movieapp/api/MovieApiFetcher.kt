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

    fun getMovieList(movieList: MutableLiveData<ArrayList<Movie>>, query: String) {
        val service = movieApi.getSearchResult(NAVER_CLIENT, NAVER_KEY, query)
        service.enqueue(object : Callback<ResultGetSearchNews> {
            override fun onResponse(call: Call<ResultGetSearchNews>, response: Response<ResultGetSearchNews>) {
                if(response.isSuccessful){
                    movieList.postValue(response.body()!!.items as ArrayList<Movie>)
                }
                Log.e("에러", response.body()!!.toString())
            }

            override fun onFailure(call: Call<ResultGetSearchNews>, t: Throwable) {

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