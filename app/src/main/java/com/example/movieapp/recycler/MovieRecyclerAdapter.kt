package com.example.movieapp.recycler

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.ListMovieBinding
import com.example.movieapp.model.Movie

class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<Movie>()
    private lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        view = parent
        val mBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    internal fun setData(newItems: ArrayList<Movie>) {
        movieList = newItems
        notifyDataSetChanged()
    }
    internal fun addData(newItems: ArrayList<Movie>) {
        val last = movieList.size
        movieList += newItems
        notifyItemInserted(last)
    }
    internal fun resetData() {
        movieList = ArrayList()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(private val mBinding: ListMovieBinding): RecyclerView.ViewHolder(mBinding.root) {
        fun bind(movie: Movie) {
            mBinding.apply {
                setMovie(movie)

                root.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.link))
                    root.context.startActivity(intent)
                }
            }
            if(movie.image.isBlank()) {
                loadDefaultImgae()
            }else {
                loadImage(movie.image)
            }
        }
        private fun loadImage(image: String) {
            mBinding.apply {
                Glide.with(root)
                    .load(image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgMovie)
            }
        }
        private fun loadDefaultImgae() {
            mBinding.apply {
                Glide.with(root)
                    .load(root.context.getString(R.string.default_image_url))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgMovie)
            }
        }
    }
}