package com.adrian.bucayan.movie.presentation.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrian.bucayan.movie.R
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
            viewModel.setGetMovieEvent(GetMovieEvent.GetGetMovieEvents, query)
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

            /*addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                @SuppressLint("TimberArgCount")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linearLayoutManager = layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                    Timber.e("lastVisibleItemPosition = %s", lastVisibleItemPosition)
                    val listItemIndex = recipeListAdapter!!.getListItems().size - 1
                    Timber.e("listItemIndex = %s", listItemIndex)

                    var limitOffset = page?.times(PAGE_SIZE)
                    Timber.e("listItemIndex = %s", listItemIndex)

                    if (binding.linearProgressBarLoadMore.visibility == View.GONE
                        && (lastVisibleItemPosition) == listItemIndex
                        && limitOffset!! < totalItemCount) {
                        //displayLoadMoreProgressBar(true)
                        var newPage = page?.plus(1)
                        apiCall(newPage)
                    }
                }
            })*/
        }

    }

    private fun gotoSelectedMovie(search: Search, position: Int?) {
        Timber.e("search = %s", search.title)
    }

    private fun subscribeObservers() {
        viewModel.dataStateMovie.observe(viewLifecycleOwner) { dataStateMovie ->
            when (dataStateMovie) {

                is Resource.Success<MovieResponse> -> {
                    Timber.e("dataStateMovie SUCCESS")
                    displayLoading(false)
                    binding.movieRecyclerview.visibility = View.VISIBLE
                    binding.tvEmptyMsg.visibility = View.GONE
                    Timber.d("search list = " + dataStateMovie.data?.search!!.size)
                    movieListAdapter!!.submitList(dataStateMovie.data.search!!)
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