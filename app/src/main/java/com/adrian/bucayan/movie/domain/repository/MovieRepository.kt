package com.adrian.bucayan.movie.domain.repository

import com.adrian.bucayan.movie.data.dto.MovieResponseDto


interface MovieRepository {

    suspend fun get(searchedText: String): MovieResponseDto

}