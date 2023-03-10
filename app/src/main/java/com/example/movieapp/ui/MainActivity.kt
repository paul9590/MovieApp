package com.example.movieapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.db.DBRepository
import com.example.movieapp.db.Recent
import com.example.movieapp.recycler.MovieRecyclerAdapter
import com.example.movieapp.vm.MovieViewModel


class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private val movieViewModel = MovieViewModel()
    private val db = DBRepository.get()
    private val movieRecyclerAdapter = MovieRecyclerAdapter()
    private var isCalling = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val inputText = result.data!!.getStringExtra("recentKeyword")!!
                mBinding.editSearch.setText(inputText)
                search(inputText)
            }
        }

        mBinding.apply {
            btnSearch.setOnClickListener {
                search(editSearch.text.toString())
            }

            btnRecent.setOnClickListener {
                val intent = Intent(applicationContext, RecentActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                getResult.launch(intent)
            }

            recyclerMovie.adapter = movieRecyclerAdapter
            recyclerMovie.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            recyclerMovie.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(recyclerView.canScrollVertically(-1)) {
                        if(isCalling) return
                        isCalling = true
                        movieViewModel.getMoreMovieList(movieRecyclerAdapter.itemCount + 1)
                    }
                }
            })
        }

        observeData()
        setContentView(mBinding.root)
    }

    private fun observeData() {
        movieViewModel.movieList.observe(this) {
            isCalling = false
            movieViewModel.movieList.value?.let { movieRecyclerAdapter.setData(it)}
        }

        movieViewModel.moreMovieList.observe(this) {
            isCalling = false
            movieViewModel.moreMovieList.value?.let { movieRecyclerAdapter.addData(it)}
        }
    }

    private fun search(query: String) {
        isCalling = true
        movieRecyclerAdapter.resetData()
        movieViewModel.getMovieList(query)
        db.addRecent(Recent(query))
    }
}