package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class MovieTranslation(
    @Json(name = "data")
    val data: TranslationData,
    @Json(name = "english_name")
    val english_name: String,
    @Json(name = "iso_3166_1")
    val iso_3166_1: String,
    @Json(name = "iso_639_1")
    val iso_639_1: String,
    @Json(name = "name")
    val name: String
)