package com.adrian.bucayan.movie.common

import com.adrian.bucayan.movie.BuildConfig

/** singleton object of Constants value in the application */
object Constants {
    const val APP_VERSION = BuildConfig.VERSION_NAME
    const val BASE_URL = "http://www.omdbapi.com/"
    const val API_KEY = "e83ba198"
}
