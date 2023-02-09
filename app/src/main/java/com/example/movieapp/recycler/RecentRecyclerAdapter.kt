package com.example.movieapp.recycler

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ListRecentBinding
import com.example.movieapp.db.Recent

class RecentRecyclerAdapter : RecyclerView.Adapter<RecentRecyclerAdapter.RecentViewHolder>() {
    private var recentList = ArrayList<Recent>()
    private lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        view = parent
        val mBinding = ListRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(recentList[position])
    }

    internal fun setData(newItems: ArrayList<Recent>) {
        recentList = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    class RecentViewHolder(private val mBinding: ListRecentBinding): RecyclerView.ViewHolder(mBinding.root) {
        fun bind(recent: Recent) {
            mBinding.name = recent.name
            mBinding.root.setOnClickListener {
                val activity = mBinding.root.context as Activity
                val intent = Intent()
                intent.putExtra("search", recent.name)
                activity.setResult(RESULT_OK, intent)
                activity.finish()
            }
        }
    }
}