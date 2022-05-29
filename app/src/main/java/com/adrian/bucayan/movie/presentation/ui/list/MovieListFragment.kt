package com.adrian.bucayan.movie.presentation.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.bucayan.movie.R
import com.adrian.bucayan.movie.common.Constants
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.databinding.FragmentMovieListBinding
import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.adrian.bucayan.movie.domain.model.Search
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private val viewModel: MovieListViewModel by  viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: MaterialToolbar

    private var movieListAdapter : MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieListBinding.bind(view)
        initViews()
        subscribeObservers()
        //viewModel.setGetMovieEvent(GetMovieEvent.GetGetMovieEvents)
    }

    private fun initViews() {
        setHasOptionsMenu(true)
        displaySearchViewMenuItem(true)
        initRecyclerview()
    }

    private fun displaySearchViewMenuItem(isVisible: Boolean) {
        toolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.inflateMenu(R.menu.home_menu)
        val searchViewItem = toolbar.menu.findItem(R.id.appbar_search)
        val searchView = searchViewItem?.actionView as SearchView
        if (isVisible) {
            toolbar.setNavigationIcon(R.drawable.baseline_movie_filter_black_24dp)
            toolbar.setNavigationIconTint(R.color.colorTextButton)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    showSearchResult(query)
                    Timber.d("SUBMIT")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //showSearchResult(newText)
                    return false
                }
            })
            searchView.visibility = View.VISIBLE
        }
        else {
            searchView.clearFocus()
            searchView.visibility = View.GONE
            toolbar.menu.clear()
        }
    }

    private fun showSearchResult(query: String?) {
        if (query != null) {
            viewModel.setGetMovieEvent(MovieIntent.GetMovieIntents, query)
        }
    }

    private fun initRecyclerview() {
        binding.movieRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            val itemDecoration: MovieListAdapter.TopSpacingDecoration =
                MovieListAdapter.TopSpacingDecoration(20)
            addItemDecoration(itemDecoration)

            movieListAdapter = MovieListAdapter()
            movieListAdapter!!.toSelectSearch = this@MovieListFragment::gotoSelectedMovie
            adapter = movieListAdapter
        }

    }

    private fun gotoSelectedMovie(search: Search, position: Int?) {
        Timber.e("search = %s", search.title)
        val bundle = Bundle()
        bundle.putParcelable(Constants.SEARCH, search)
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }

    private fun subscribeObservers() {
        viewModel.dataStateMovie.observe(viewLifecycleOwner) { dataStateMovie ->
            when (dataStateMovie) {

                is Resource.Success<MovieResponse> -> {
                    Timber.e("dataStateMovie SUCCESS")
                    displayLoading(false)
                    binding.movieRecyclerview.visibility = View.VISIBLE
                    binding.tvEmptyMsg.visibility = View.GONE

                    val search : List<Search>? =  dataStateMovie.data?.search
                    if (!search.isNullOrEmpty()) {
                        movieListAdapter!!.submitList(search)
                    } else {
                        Toast.makeText(requireActivity().applicationContext,
                            "ERROR : " + dataStateMovie.data?.error, Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                is Resource.Error -> {
                    Timber.e("dataStateMovie ERROR %s", dataStateMovie.message)
                    displayLoading(false)
                }

                is Resource.Loading -> {
                    Timber.e("dataStateMovie LOADING")
                    displayLoading(true)
                }
            }
        }
    }

    private fun displayLoading(isDisplayed: Boolean) {
        if (isDisplayed) {
            binding.linearProgressBarLoadMore.visibility = View.VISIBLE
        } else {
            binding.linearProgressBarLoadMore.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}