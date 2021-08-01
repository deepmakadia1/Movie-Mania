package com.moviemania.ui.fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviemania.data.DataRepository
import com.moviemania.data.model.MovieResponseModel
import com.moviemania.data.paging.MoviePageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    fun getMovies(
        context:Context,
        category: String,
    ): Flow<PagingData<MovieResponseModel.MovieModel>> {
        return Pager(config = PagingConfig(20)) {
            MoviePageSource(context,dataRepository, category)
        }.flow.cachedIn(viewModelScope)
    }


}