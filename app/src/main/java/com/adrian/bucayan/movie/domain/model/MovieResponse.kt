package com.adrian.bucayan.movie.domain.model

import android.os.Parcelable
import com.adrian.bucayan.movie.data.dto.SearchDto
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class MovieResponse(
    var response: String?,
    var search: List<Search>?,
    var totalResults: String?

): Parcelable {

}
