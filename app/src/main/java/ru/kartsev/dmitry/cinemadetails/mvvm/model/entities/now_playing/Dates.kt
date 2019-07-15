package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.now_playing

import com.squareup.moshi.Json

data class Dates(
    @Json(name = "maximum")
    val maximum: String,
    @Json(name = "minimum")
    val minimum: String
)