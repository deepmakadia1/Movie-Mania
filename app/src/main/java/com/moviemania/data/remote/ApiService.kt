package com.moviemania.data.remote

import com.moviemania.data.model.GenresResponseModel
import com.moviemania.data.model.MovieResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @QueryMap map: HashMap<String, Any>
    ): MovieResponseModel


    @GET("search/movie")
    suspend fun getSearchMovieList(
        @QueryMap map: HashMap<String, Any>
    ): MovieResponseModel


    @GET("genre/movie/list")
    suspend fun getGenreList(
        @QueryMap map: HashMap<String, Any>
    ): GenresResponseModel

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @QueryMap map: HashMap<String, Any>
    ): MovieResponseModel



}