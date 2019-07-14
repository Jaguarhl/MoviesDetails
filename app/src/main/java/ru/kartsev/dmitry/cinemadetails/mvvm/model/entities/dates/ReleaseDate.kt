package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates

import com.squareup.moshi.Json

data class ReleaseDate(
    @Json(name = "certification")
    val certification: String,
    @Json(name = "iso_639_1")
    val iso_639_1: String,
    @Json(name = "note")
    val note: String,
    @Json(name = "release_date")
    val release_date: String,
    @Json(name = "type")
    val type: Int
)