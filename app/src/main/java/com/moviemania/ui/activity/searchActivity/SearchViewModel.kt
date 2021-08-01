package com.moviemania.ui.activity.searchActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviemania.data.DataRepository
import com.moviemania.data.model.MovieResponseModel
import com.moviemania.data.paging.MoviePageSource
import com.moviemania.data.paging.MoviesByGenrePageSource
import com.moviemania.data.paging.SearchMoviePageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    fun getSearchMovies(
        query: String
    ): Flow<PagingData<MovieResponseModel.MovieModel>> {
        return Pager(config = PagingConfig(20, enablePlaceholders = false)) {
            SearchMoviePageSource(dataRepository, query)
        }.flow.cachedIn(viewModelScope)
    }

    fun getMoviesByGenre(
        id: Int
    ): Flow<PagingData<MovieResponseModel.MovieModel>> {
        return Pager(config = PagingConfig(20, enablePlaceholders = false)) {
            MoviesByGenrePageSource(dataRepository, id)
        }.flow.cachedIn(viewModelScope)
    }

}