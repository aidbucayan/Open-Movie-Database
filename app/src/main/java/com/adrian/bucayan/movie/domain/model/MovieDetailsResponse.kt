package com.adrian.bucayan.movie.domain.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class MovieDetailsResponse(

    var Poster: String?,

    var Plot: String?,

    var Language: String?,

    var Actors: String?,

    var Runtime: String?,

    /*var Awards: String?,

    var BoxOffice: String?,

    var Country: String?,

    var DVD: String?,

    var Director: String?,

    var Genre: String?,

    var Metascore: String?,

    var Production: String?,

    var Rated: String?,

    //var Ratings: List<RatingDto>?,

    var Released: String?,

    var Response: String?,

    var Title: String?,

    var Type: String?,

    var Website: String?,

    var Writer: String?,

    var Year: String?,

    var imdbID: String?,

    var imdbRating: String?,

    var imdbVotes: String?*/

): Parcelable {

}
