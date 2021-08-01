package com.moviemania.ui.activity.searchActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.moviemania.R
import com.moviemania.data.model.GenresResponseModel
import com.moviemania.databinding.ActivitySearchBinding
import com.moviemania.ui.adapter.MovieListAdapter
import com.moviemania.util.Const
import com.moviemania.util.ViewTypeEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var movieAdapter: MovieListAdapter
    private val searchViewModel: SearchViewModel by viewModels()
    private var query: String = ""
    private lateinit var genre: GenresResponseModel.Genre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movieAdapter = MovieListAdapter(this, (binding.rvMovie.layoutManager as GridLayoutManager))
        binding.rvMovie.adapter = movieAdapter

        if (intent.hasExtra("query")) {
            query = intent.getStringExtra("query") ?: ""
            supportActionBar?.title = query
            callSearchMovieApi()
        }

        if (intent.hasExtra("genre")) {
            genre =
                intent.getParcelableExtra<GenresResponseModel.Genre>("genre") as GenresResponseModel.Genre
            supportActionBar?.title = genre.name
            callMoviesByGenreApi(genre.id)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchMenu = menu?.findItem(R.id.menuSearch)
        searchMenu?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuViewSwitch) {
            if ((binding.rvMovie.layoutManager as GridLayoutManager).spanCount == 1) {
                (binding.rvMovie.layoutManager as GridLayoutManager).spanCount = 3
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_grid)
                item.title = getString(R.string.grid_view)
            } else {
                (binding.rvMovie.layoutManager as GridLayoutManager).spanCount = 1
                item.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_format_list_bulleted)
                item.title = getString(R.string.list_view)
            }
            movieAdapter.notifyItemRangeChanged(0, movieAdapter.itemCount)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun callSearchMovieApi() {

        lifecycleScope.launchWhenCreated {
            searchViewModel.getSearchMovies(query).collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }

    private fun callMoviesByGenreApi(id: Int) {

        lifecycleScope.launchWhenCreated {
            searchViewModel.getMoviesByGenre(id).collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}