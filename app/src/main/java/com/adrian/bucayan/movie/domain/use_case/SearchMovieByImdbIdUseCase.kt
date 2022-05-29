package com.adrian.bucayan.movie.domain.use_case

import com.adrian.bucayan.movie.R
import com.adrian.bucayan.movie.common.Resource
import com.adrian.bucayan.movie.data.dto.toMovieDetailsResponse
import com.adrian.bucayan.movie.data.dto.toMovieResponse
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.adrian.bucayan.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMovieByImdbIdUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(imdbId: String): Flow<Resource<MovieDetailsResponse>> = flow {
        try {
            emit(Resource.Loading<MovieDetailsResponse>())
            val getSearchedMovie = repository.getMovieDetailsById(imdbId).toMovieDetailsResponse()
            emit(Resource.Success<MovieDetailsResponse>(getSearchedMovie))
        } catch(e: HttpException) {
            emit(Resource.Error<MovieDetailsResponse>(R.string.oops_something_went_wrong.toString()))
        } catch(e: IOException) {
            emit(Resource.Error<MovieDetailsResponse>(R.string.error_couldnt_reach_server.toString()))
        }
    }

}