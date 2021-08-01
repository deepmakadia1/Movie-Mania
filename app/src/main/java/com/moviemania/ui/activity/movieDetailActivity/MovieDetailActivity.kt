package com.moviemania.ui.activity.movieDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.moviemania.R
import com.moviemania.data.model.MovieResponseModel
import com.moviemania.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie:MovieResponseModel.MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra("movie")){
            movie = intent.getParcelableExtra<MovieResponseModel.MovieModel>("movie") as MovieResponseModel.MovieModel
            binding.movie = movie
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}