package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class ProductionCountry(
    @Json(name = "iso_3166_1")
    var iso_3166_1: String? = null,
    @Json(name = "name")
    var name: String? = null
)