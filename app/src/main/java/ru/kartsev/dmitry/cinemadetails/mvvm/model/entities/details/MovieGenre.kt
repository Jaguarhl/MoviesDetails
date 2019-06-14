package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class MovieGenre(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "name")
    var name: String? = null
)