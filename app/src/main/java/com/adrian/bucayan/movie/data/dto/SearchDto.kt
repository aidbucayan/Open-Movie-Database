package com.adrian.bucayan.movie.data.dto

import com.adrian.bucayan.movie.domain.model.Search
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchDto(

    @JsonProperty("Poster")
    var poster: String?,

    @JsonProperty("Title")
    var title: String?,

    @JsonProperty("Type")
    var type: String?,

    @JsonProperty("Year")
    var year: String?,

    @JsonProperty("imdbID")
    var imdbID: String?
)

fun SearchDto.toSearch(): Search {
    return  Search(
        poster = poster,
        title = title,
        type = type,
        year = year,
        imdbID = imdbID
    )
}

