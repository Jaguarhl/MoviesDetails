package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class ProductionCompany(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "logo_path")
    var logo_path: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "origin_country")
    var origin_country: String? = null
)