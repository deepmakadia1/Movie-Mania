package com.moviemania.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moviemania.data.DataRepository
import com.moviemania.data.model.MovieResponseModel
import com.moviemania.data.remote.LANGUAGE
import com.moviemania.data.remote.PAGE
import com.moviemania.data.remote.QUERY
import retrofit2.HttpException
import java.io.IOException

class SearchMoviePageSource(val dataRepository: DataRepository, val query: String) :
    PagingSource<Int, MovieResponseModel.MovieModel>() {

    companion object {
        private val DEFAULT_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponseModel.MovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseModel.MovieModel> {

        val page = params.key ?: DEFAULT_PAGE_INDEX

        val hashMap = HashMap<String, Any>()
        hashMap[PAGE] = page
        hashMap[LANGUAGE] = "en-US"
        hashMap[QUERY] = query

        return try {
            val response = dataRepository.getSearchMovies(hashMap)
            if (!response.results.isNullOrEmpty()) {
                LoadResult.Page(
                    response.results,
                    prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                    nextKey = if (response.results.isNullOrEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Error in parsing response"))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}