package com.adrian.bucayan.movie.data.dto

import com.adrian.bucayan.movie.domain.model.MovieDetailsResponse
import com.adrian.bucayan.movie.domain.model.MovieResponse
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MovieDetailsResponseDto(

    @JsonProperty("Actors") var Actors: String?,

    @JsonProperty("Awards") var Awards: String?,

    @JsonProperty("BoxOffice") var BoxOffice: String?,

    @JsonProperty("Country") var Country: String?,

    @JsonProperty("DVD") var DVD: String?,

    @JsonProperty("Director") var Director: String?,

    @JsonProperty("Genre") var Genre: String?,

    @JsonProperty("Language") var Language: String?,

    @JsonProperty("Metascore") var Metascore: String?,

    @JsonProperty("Plot") var Plot: String?,

    @JsonProperty("Poster") var Poster: String?,

    @JsonProperty("Production") var Production: String?,

    @JsonProperty("Rated") var Rated: String?,

    //@JsonProperty("Ratings") var Ratings: List<RatingDto>?,

    @JsonProperty("Released") var Released: String?,

    @JsonProperty("Response") var Response: String?,

    @JsonProperty("Runtime") var Runtime: String?,

    @JsonProperty("Title") var Title: String?,

    @JsonProperty("Type") var Type: String?,

    @JsonProperty("Website") var Website: String?,

    @JsonProperty("Writer") var Writer: String?,

    @JsonProperty("Year") var Year: String?,

    @JsonProperty("imdbID") var imdbID: String?,

    @JsonProperty("imdbRating") var imdbRating: String?,

    @JsonProperty("imdbVotes") var imdbVotes: String?

)
fun MovieDetailsResponseDto.toMovieDetailsResponse(): MovieDetailsResponse {
    return  MovieDetailsResponse(
        Poster = Poster,
        Plot = Plot,
        Language = Language,
        Actors = Actors,
        Runtime = Runtime
    )
}

