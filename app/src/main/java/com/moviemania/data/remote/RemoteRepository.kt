package com.moviemania.data.remote

import com.moviemania.data.model.GenresResponseModel
import com.moviemania.data.model.MovieResponseModel
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovieList(category: String, map: HashMap<String, Any>): MovieResponseModel {
        return apiService.getMovieList(category, map)
    }

    suspend fun getSearchMovieList(map: HashMap<String, Any>): MovieResponseModel {
        return apiService.getSearchMovieList(map)
    }

    suspend fun getGenreList(map: HashMap<String, Any>): GenresResponseModel {
        return apiService.getGenreList(map)
    }

    suspend fun getMoviesByGenre(map: HashMap<String, Any>): MovieResponseModel {
        return apiService.getMoviesByGenre(map)
    }

}