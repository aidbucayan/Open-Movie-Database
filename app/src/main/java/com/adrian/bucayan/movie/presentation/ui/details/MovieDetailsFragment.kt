package com.adrian.bucayan.movie.presentation.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import com.adrian.bucayan.movie.R
import com.adrian.bucayan.movie.common.Constants
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.databinding.FragmentMovieDetailsBinding
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import com.adrian.bucayan.movie.domain.model.Search
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private val viewModel: MovieDetailsViewModel by  viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var search: Search
    private lateinit var toolbar: MaterialToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search = arguments?.getParcelable(Constants.SEARCH)!!
        _binding = FragmentMovieDetailsBinding.bind(view)
        subscribeObservers()
        displaySearchViewMenuItem(false)
        initViews()
        search.imdbID?.let {
            viewModel.setGetMovieDetailsEvent(MovieDetailsIntent.GetMovieDetailsIntents,
                it
            )
        }
    }

    private fun displaySearchViewMenuItem(isVisible: Boolean) {
        toolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.inflateMenu(R.menu.home_menu)
        val searchViewItem = toolbar.menu.findItem(R.id.appbar_search)
        val searchView = searchViewItem?.actionView as SearchView
        if (!isVisible) {
            searchView.clearFocus()
            searchView.visibility = View.GONE
            toolbar.menu.clear()
        }
    }

    private fun initViews() {

        binding.tvMovieDetailsPoster.load(search.poster) {
            crossfade(true)
            placeholder(R.drawable.img)
            scale(Scale.FILL)
        }

        binding.tvMovieDetailsTitle.text = search.title
        binding.tvMovieItemsYear.text = "Year : " + search.year
        binding.tvMovieItemsImdbID.text = "IMDB ID : " +  search.imdbID
        binding.tvMovieItemsType.text = "Type : " +   search.type

    }

    private fun subscribeObservers() {
        viewModel.dataStateMovieDetails.observe(viewLifecycleOwner) { dataStateMovieDetails ->
            when (dataStateMovieDetails) {

                is Resource.Success<MovieDetailsResponse> -> {
                    Timber.e("dataStateMovie SUCCESS")
                    //displayLoading(false)
                    var movieDetailsResponse : MovieDetailsResponse? = dataStateMovieDetails.data
                    if (movieDetailsResponse != null) {
                        binding.tvMovieItemsLongPlot.text = "Plot : " + movieDetailsResponse.Plot
                        binding.tvMovieItemsLanguage.text = "Language(s) : " + movieDetailsResponse.Language
                        binding.tvMovieItemsActors.text = "Actors : " + movieDetailsResponse.Actors
                        binding.tvMovieItemsDuration.text = "Duration : " + movieDetailsResponse.Runtime
                    }

                }

                is Resource.Error -> {
                    Timber.e("dataStateMovie ERROR %s", dataStateMovieDetails.message)
                    //displayLoading(false)
                }

                is Resource.Loading -> {
                    Timber.e("dataStateMovie LOADING")
                    //displayLoading(true)
                }
            }
        }
    }


}