package com.adrian.bucayan.movie.domain.repository

import com.adrian.bucayan.movie.data.dto.MovieDetailsResponseDto
import com.adrian.bucayan.movie.data.dto.MovieResponseDto
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse


interface MovieRepository {

    suspend fun getMovieListBySearch(searchedText: String): MovieResponseDto

    suspend fun getMovieDetailsById(searchedText: String): MovieDetailsResponseDto

}