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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recentRecyclerAdapter = RecentRecyclerAdapter()
        val mBinding = ActivityRecentBinding.inflate(layoutInflater).apply {
            recyclerRecent.adapter = recentRecyclerAdapter
            recyclerRecent.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        }
        val db = DBRepository.get()


        db.getRecent().observe(this) {
            recentRecyclerAdapter.setData(it as ArrayList<Recent>)
        }

        setContentView(mBinding.root)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}