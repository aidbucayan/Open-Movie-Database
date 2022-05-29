package com.adrian.bucayan.movie.domain.use_case

import com.adrian.bucayan.movie.R
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.data.dto.toMovieResponse
import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.adrian.bucayan.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(searchedText: String): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading<MovieResponse>())
            val getSearchedMovie = repository.get(searchedText).toMovieResponse()
            emit(Resource.Success<MovieResponse>(getSearchedMovie))
        } catch(e: HttpException) {
            emit(Resource.Error<MovieResponse>(R.string.oops_something_went_wrong.toString()))
        } catch(e: IOException) {
            emit(Resource.Error<MovieResponse>(R.string.error_couldnt_reach_server.toString()))
        }
    }

}