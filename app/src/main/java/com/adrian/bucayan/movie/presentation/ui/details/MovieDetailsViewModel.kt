package com.adrian.bucayan.movie.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import com.adrian.bucayan.movie.domain.use_case.SearchMovieByImdbIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val searchMovieByImdbIdUseCase: SearchMovieByImdbIdUseCase
) : ViewModel() {

    private val _dataStateMovieDetails: MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()

    val dataStateMovieDetails: LiveData<Resource<MovieDetailsResponse>> = _dataStateMovieDetails

    fun setGetMovieDetailsEvent(movieDetailsIntent: MovieDetailsIntent, searchedText: String) {
        viewModelScope.launch {
            when(movieDetailsIntent) {
                is MovieDetailsIntent.GetMovieDetailsIntents -> {
                    searchMovieByImdbIdUseCase(searchedText)
                        .onEach { dataStateMovie ->
                            _dataStateMovieDetails.value = dataStateMovie
                        }
                        .launchIn(viewModelScope)
                }

                is MovieDetailsIntent.None -> {

                }
            }
        }
    }

}

sealed class MovieDetailsIntent {

    object GetMovieDetailsIntents: MovieDetailsIntent()

    object None: MovieDetailsIntent()
}