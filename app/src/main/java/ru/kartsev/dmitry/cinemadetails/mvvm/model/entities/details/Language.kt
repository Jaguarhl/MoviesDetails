package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class Language(
    @Json(name = "iso_639_1")
    var iso6391: String? = null,
    @Json(name = "name")
    var name: String? = null
)