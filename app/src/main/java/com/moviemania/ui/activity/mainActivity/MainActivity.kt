package com.moviemania.ui.activity.mainActivity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.moviemania.R
import com.moviemania.data.model.GenresResponseModel
import com.moviemania.data.remote.LANGUAGE
import com.moviemania.data.remote.PAGE
import com.moviemania.databinding.ActivityMainBinding
import com.moviemania.ui.activity.searchActivity.SearchActivity
import com.moviemania.ui.adapter.GenreAdapter
import com.moviemania.ui.adapter.MoviePagerAdapter
import com.moviemania.ui.fragment.MovieFragment
import com.moviemania.util.Const.POPULAR
import com.moviemania.util.Const.TOP_RATED
import com.moviemania.util.Const.UPCOMING
import com.moviemania.util.ViewTypeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var moviePagerAdapter: MoviePagerAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private var currentViewState = ViewTypeEnum.GRID
    private var genreList: ArrayList<GenresResponseModel.Genre> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        moviePagerAdapter = MoviePagerAdapter(supportFragmentManager, lifecycle)
        moviePagerAdapter.addFrag(MovieFragment.newInstance(UPCOMING))
        moviePagerAdapter.addFrag(MovieFragment.newInstance(POPULAR))
        moviePagerAdapter.addFrag(MovieFragment.newInstance(TOP_RATED))

        val tabNames = arrayOf(UPCOMING, POPULAR, TOP_RATED)

        binding.pagerMovie.adapter = moviePagerAdapter

        TabLayoutMediator(binding.tabMovie, binding.pagerMovie) { tab, position ->
            tab.text = tabNames[position].replace("_", " ")
        }.attach()

        binding.fabFilter.setOnClickListener {

            if (genreList.isNotEmpty()) {
                showGenreDialog()
            }

        }

        handleApiResponse()

        val hashMap = HashMap<String, Any>()
        hashMap[LANGUAGE] = "en-US"
        mainViewModel.getGenres(hashMap)

    }

    private fun showGenreDialog() {

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_genre)
        dialog.setCancelable(true)
        val list: RecyclerView = dialog.findViewById(R.id.rvGenre)
        list.adapter = GenreAdapter(this, genreList) {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun handleApiResponse() {

        mainViewModel.genreLiveData.observe(this) {
            if (it != null) {
                genreList.clear()
                genreList.addAll(it)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchMenu = menu?.findItem(R.id.menuSearch)
        val searchView = searchMenu?.actionView as SearchView

        searchView.queryHint = getString(R.string.search_movie)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                startActivity(
                    Intent(
                        this@MainActivity,
                        SearchActivity::class.java
                    ).putExtra("query", query)
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuViewSwitch) {
            if (currentViewState == ViewTypeEnum.GRID) {
                currentViewState = ViewTypeEnum.LIST
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_grid)
                item.title = getString(R.string.grid_view)
            } else {
                currentViewState = ViewTypeEnum.GRID
                item.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_format_list_bulleted)
                item.title = getString(R.string.list_view)
            }
            mainViewModel.changeView(currentViewState)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}