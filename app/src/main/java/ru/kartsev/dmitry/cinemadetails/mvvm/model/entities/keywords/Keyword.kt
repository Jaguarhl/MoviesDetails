package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords

import com.squareup.moshi.Json

data class Keyword(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)