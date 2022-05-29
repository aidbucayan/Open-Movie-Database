package com.adrian.bucayan.movie.data.remote

import com.adrian.bucayan.movie.data.dto.MovieResponseDto
import retrofit2.http.*

interface OpenMovieDatabaseApi {

    @GET(".")
    suspend fun getAllMovie(@Query("s") title: String,
                            @Query("apikey") apikey: String): MovieResponseDto

}