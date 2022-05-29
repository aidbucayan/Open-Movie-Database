package com.adrian.bucayan.movie.domain.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Search(
    var poster: String?,
    var title: String?,
    var type: String?,
    var year: String?,
    var imdbID: String?
): Parcelable {

}
