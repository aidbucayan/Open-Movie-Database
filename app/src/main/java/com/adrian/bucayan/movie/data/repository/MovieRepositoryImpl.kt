package com.adrian.bucayan.movie.data.repository

import com.adrian.bucayan.movie.common.Constants
import com.adrian.bucayan.movie.data.dto.MovieResponseDto
import com.adrian.bucayan.movie.data.remote.OpenMovieDatabaseApi
import com.adrian.bucayan.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: OpenMovieDatabaseApi
) : MovieRepository {

    override suspend fun get(searchedText: String): MovieResponseDto {
        return api.getAllMovie(searchedText,Constants.API_KEY)
    }

}