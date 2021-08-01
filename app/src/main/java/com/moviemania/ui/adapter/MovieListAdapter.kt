package com.moviemania.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviemania.data.model.MovieResponseModel.*
import com.moviemania.databinding.ItemMovieGridBinding
import com.moviemania.databinding.ItemMovieListBinding
import com.moviemania.ui.activity.movieDetailActivity.MovieDetailActivity
import com.moviemania.util.ViewTypeEnum

class MovieListAdapter(private val context: Context, private val layoutManager: GridLayoutManager) :
    PagingDataAdapter<MovieModel, RecyclerView.ViewHolder>(
        MovieDiffCallback()
    ) {


    class MovieGridViewHolder(val context: Context, private val binding: ItemMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel) {
            binding.movie = movie

            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(context, MovieDetailActivity::class.java)
                        .putExtra("movie", movie)
                )
            }
        }
    }

    class MovieListViewHolder(val context: Context, private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel) {
            binding.movie = movie

            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(context, MovieDetailActivity::class.java)
                        .putExtra("movie", movie)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager.spanCount == 1) {
            ViewTypeEnum.LIST.ordinal
        } else {
            ViewTypeEnum.GRID.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ViewTypeEnum.GRID.ordinal -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemGridBinding = ItemMovieGridBinding.inflate(inflater, parent, false)
                return MovieGridViewHolder(context, itemGridBinding)
            }
            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemListBinding = ItemMovieListBinding.inflate(inflater, parent, false)
                return MovieListViewHolder(context, itemListBinding)
            }
        }

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieGridViewHolder) {
            if (getItem(position) != null) {
                getItem(position)?.let { holder.bind(it) }
            }
        } else {
            (holder as MovieListViewHolder)
            if (getItem(position) != null) {
                getItem(position)?.let { holder.bind(it) }
            }
        }

    }
}