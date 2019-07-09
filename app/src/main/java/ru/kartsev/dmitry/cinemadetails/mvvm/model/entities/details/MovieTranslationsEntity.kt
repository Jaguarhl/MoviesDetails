package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class MovieTranslationsEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "translations")
    val translations: List<MovieTranslation>?
)