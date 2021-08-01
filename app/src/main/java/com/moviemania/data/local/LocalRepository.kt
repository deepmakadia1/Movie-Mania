package com.moviemania.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moviemania.data.model.MovieResponseModel

@Database(entities = [MovieResponseModel.MovieModel::class], version = 1, exportSchema = false)
abstract class LocalRepository : RoomDatabase(){

    abstract fun localDao(): LocalDao

    suspend fun insertMovie(movie : MovieResponseModel.MovieModel){
        localDao().insertMovies(movie)
    }

    suspend fun getMovies(movieCategory:String):List<MovieResponseModel.MovieModel>{
        return localDao().getMovies(movieCategory)
    }

}