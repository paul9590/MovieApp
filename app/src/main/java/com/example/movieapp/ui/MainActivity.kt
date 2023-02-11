package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.db.DBRepository
import com.example.movieapp.db.Recent
import com.example.movieapp.recycler.MovieRecyclerAdapter
import com.example.movieapp.vm.MovieViewModel


const val REQUEST_CODE = 100
class MainActivity : AppCompatActivity() {

    private val movieViewModel = MovieViewModel()
    private val movieRecyclerAdapter = MovieRecyclerAdapter()

    private val db = DBRepository.get()

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private var calling = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        mBinding.apply {

            btnSearch.setOnClickListener {
                search(editSearch.text.toString())
            }
            btnRecent.setOnClickListener {
                val intent = Intent(applicationContext, RecentActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivityForResult(intent, REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            val inputText = data!!.getStringExtra("search")!!
            mBinding.editSearch.setText(inputText)
            search(inputText)
        }

    }

    private fun search(query: String) {
        calling = true
        movieRecyclerAdapter.resetData()
        movieViewModel.getMovieList(query)
        db.addRecent(Recent(query))
    }
}