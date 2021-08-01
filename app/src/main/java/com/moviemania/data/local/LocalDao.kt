package com.moviemania.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moviemania.data.model.MovieResponseModel

@Dao
interface LocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: MovieResponseModel.MovieModel)


    @Query("SELECT * FROM movie_list WHERE category LIKE :movieCategory LIMIT 20")
    suspend fun getMovies(movieCategory: String): List<MovieResponseModel.MovieModel>

}
