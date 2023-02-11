package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ActivityRecentBinding
import com.example.movieapp.db.DBRepository
import com.example.movieapp.db.Recent
import com.example.movieapp.recycler.RecentRecyclerAdapter

class RecentActivity : AppCompatActivity() {

    private val db = DBRepository.get()
    private val recentRecyclerAdapter = RecentRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mBinding = ActivityRecentBinding.inflate(layoutInflater).apply {
            recyclerRecent.adapter = recentRecyclerAdapter
            recyclerRecent.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        }

        observeData()
        setContentView(mBinding.root)
    }

    private fun observeData() {
        db.getRecent().observe(this) {
            recentRecyclerAdapter.setData(it as ArrayList<Recent>)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}