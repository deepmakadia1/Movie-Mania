package com.moviemania.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.moviemania.data.model.GenresResponseModel
import com.moviemania.databinding.ItemGenreBinding
import com.moviemania.ui.activity.searchActivity.SearchActivity
import java.util.*

class GenreAdapter(
    var context: Context,
    var genreList: List<GenresResponseModel.Genre>,
    var onGenreClicked: () -> Unit = {}
) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenresResponseModel.Genre) {

            binding.genre = genre
            binding.tvGenre.setOnClickListener {
                onGenreClicked()
                context.startActivity(
                    Intent(
                        context,
                        SearchActivity::class.java
                    ).putExtra("genre", genreList[absoluteAdapterPosition])
                )
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemGenreBinding = ItemGenreBinding.inflate(inflater, parent, false)
        return ViewHolder(itemGenreBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(genreList[position])

    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}