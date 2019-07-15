package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords

import com.squareup.moshi.Json

data class MovieKeywordsEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "keywords")
    val keywords: List<Keyword>
)