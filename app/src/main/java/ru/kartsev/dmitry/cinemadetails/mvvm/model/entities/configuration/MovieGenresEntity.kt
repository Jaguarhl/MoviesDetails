package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json

data class MovieGenresEntity(
    @Json(name = "genres")
    val genres: List<Genre>
)