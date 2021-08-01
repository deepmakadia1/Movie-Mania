package com.moviemania.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.moviemania.R
import com.moviemania.databinding.FragmentMovieBinding
import com.moviemania.ui.activity.mainActivity.MainViewModel
import com.moviemania.ui.adapter.MovieListAdapter
import com.moviemania.util.Const.CATEGORY
import com.moviemania.util.Const.POPULAR
import com.moviemania.util.ViewTypeEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val mainViewModel :MainViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieListAdapter(requireContext(),(binding.rvMovie.layoutManager as GridLayoutManager))
        binding.rvMovie.adapter = movieAdapter

        mainViewModel.viewTypeLiveData.observe(viewLifecycleOwner){
            if (it == ViewTypeEnum.GRID){
                (binding.rvMovie.layoutManager as GridLayoutManager).spanCount = 3
            }else{
                (binding.rvMovie.layoutManager as GridLayoutManager).spanCount = 1
            }
            movieAdapter.notifyItemRangeChanged(0, movieAdapter.itemCount)
        }

        callMovieApi()


    }

    private fun callMovieApi() {

        lifecycleScope.launchWhenCreated {
            movieViewModel.getMovies(requireContext(),arguments?.getString(CATEGORY, POPULAR) ?: POPULAR).collectLatest {
                movieAdapter.submitData(it)
            }
        }


    }

    companion object {

        fun newInstance(category: String) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, category)
                }
            }
    }
}