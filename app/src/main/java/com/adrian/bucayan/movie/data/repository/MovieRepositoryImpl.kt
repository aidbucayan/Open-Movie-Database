package com.adrian.bucayan.movie.data.repository

import com.adrian.bucayan.movie.common.Constants
import com.adrian.bucayan.movie.data.dto.MovieDetailsResponseDto
import com.adrian.bucayan.movie.data.dto.MovieResponseDto
import com.adrian.bucayan.movie.data.remote.OpenMovieDatabaseApi
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import com.adrian.bucayan.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: OpenMovieDatabaseApi
) : MovieRepository {

    override suspend fun getMovieListBySearch(searchedText: String): MovieResponseDto {
        return api.getMovieByTitle(searchedText,Constants.API_KEY)
    }

    override suspend fun getMovieDetailsById(imdbId: String): MovieDetailsResponseDto {
        return api.getMovieByImdbId(imdbId,Constants.API_KEY)
    }

}