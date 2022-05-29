package com.adrian.bucayan.movie.data.dto

import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MovieResponseDto(

    @JsonProperty("Response")
    var response: String?,

    @JsonProperty("Search")
    var search: List<SearchDto>?,

    @JsonProperty("totalResults")
    var totalResults: String?

)
fun MovieResponseDto.toMovieResponse(): MovieResponse {
    return  MovieResponse(
        response = response,
        search = search?.map { it.toSearch() },
        totalResults = totalResults,
    )
}

