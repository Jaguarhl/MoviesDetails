package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json

data class MovieAlternativeTitlesEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "titles")
    val titles: List<Title>
) {
    data class Title(
        @Json(name = "iso_3166_1")
        val iso31661: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "type")
        val type: String
    )
}