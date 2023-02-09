package com.example.movieapp.api

import android.util.Log
import com.example.movieapp.BuildConfig
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

    fun getMovieList(query: String) {
        val service = movieApi.getSearchResult(NAVER_CLIENT, NAVER_KEY, query)
        service.enqueue(object : Callback<ResultGetSearchNews> {
            override fun onResponse(call: Call<ResultGetSearchNews>, response: Response<ResultGetSearchNews>) {
                if(response.isSuccessful){
                    Log.e("리스폰스", response.body()!!.toString())
                }
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