package com.moviemania.data

import android.content.Context
import com.moviemania.BuildConfig
import com.moviemania.data.local.LocalRepository
import com.moviemania.data.model.GenresResponseModel
import com.moviemania.data.model.MovieResponseModel
import com.moviemania.data.remote.API_KEY
import com.moviemania.data.remote.RemoteRepository
import com.moviemania.util.Util
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val context: Context,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

    suspend fun getMovies(category: String, map: HashMap<String, Any>): MovieResponseModel {
        return if (Util.isNetworkConnected(context)) {
            map[API_KEY] = BuildConfig.API_KEY
            remoteRepository.getMovieList(category, map)
        }else{
            MovieResponseModel(results = localRepository.getMovies(category) as ArrayList<MovieResponseModel.MovieModel>)
        }
    }

    suspend fun getSearchMovies(map: HashMap<String, Any>): MovieResponseModel {
        map[API_KEY] = BuildConfig.API_KEY
        return remoteRepository.getSearchMovieList(map)
    }

    suspend fun getGenres(map: HashMap<String, Any>): GenresResponseModel {
        map[API_KEY] = BuildConfig.API_KEY
        return remoteRepository.getGenreList(map)
    }

    suspend fun getMoviesByGenre(map: HashMap<String, Any>): MovieResponseModel {
        map[API_KEY] = BuildConfig.API_KEY
        return remoteRepository.getMoviesByGenre(map)
    }

    suspend fun insertMovies(
        movieList: ArrayList<MovieResponseModel.MovieModel>,
        category: String
    ) {
        for (i in movieList.indices) {
            movieList[i].category = category
            localRepository.insertMovie(movieList[i])
        }
    }

}