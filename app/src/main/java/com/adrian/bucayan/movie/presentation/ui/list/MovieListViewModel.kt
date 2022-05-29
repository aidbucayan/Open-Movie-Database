package com.adrian.bucayan.movie.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.adrian.bucayan.movie.domain.use_case.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase) : ViewModel() {

    private val _dataStateMovie: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

    val dataStateMovie: LiveData<Resource<MovieResponse>> = _dataStateMovie

    fun setGetMovieEvent(getMovieEvent: GetMovieEvent, searchedText: String) {
        viewModelScope.launch {
            when(getMovieEvent) {
                is GetMovieEvent.GetGetMovieEvents -> {
                    searchMovieUseCase(searchedText)
                        .onEach { dataStateMovie ->
                            _dataStateMovie.value = dataStateMovie
                        }
                        .launchIn(viewModelScope)
                }

                is GetMovieEvent.None -> {

                }
            }
        }
    }
}

sealed class GetMovieEvent {

    object GetGetMovieEvents: GetMovieEvent()

    object None: GetMovieEvent()
}
