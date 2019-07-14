package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates

import com.squareup.moshi.Json

data class Result(
    @Json(name = "iso_3166_1")
    val iso_3166_1: String,
    @Json(name = "release_dates")
    val release_dates: List<ReleaseDate>
)