package com.adrian.bucayan.movie.data.remote

import com.adrian.bucayan.movie.data.dto.MovieDetailsResponseDto
import com.adrian.bucayan.movie.data.dto.MovieResponseDto
import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import retrofit2.http.*

interface OpenMovieDatabaseApi {

    @GET(".")
    suspend fun getMovieByTitle(@Query("s") title: String,
                                @Query("apikey") apikey: String): MovieResponseDto

    @GET(".")
    suspend fun getMovieByImdbId(@Query("i") title: String,
                                @Query("apikey") apikey: String): MovieDetailsResponseDto
}