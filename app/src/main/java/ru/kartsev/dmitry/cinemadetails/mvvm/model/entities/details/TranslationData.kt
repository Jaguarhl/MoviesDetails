package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class TranslationData(
    @Json(name = "homepage")
    val homepage: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "title")
    val title: String
)