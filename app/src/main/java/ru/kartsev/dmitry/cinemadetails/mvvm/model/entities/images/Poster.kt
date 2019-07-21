package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.images


import com.squareup.moshi.Json

data class Poster(
    @Json(name = "aspect_ratio")
    val aspect_ratio: Double,
    @Json(name = "file_path")
    val file_path: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "iso_639_1")
    val iso_639_1: String?,
    @Json(name = "vote_average")
    val vote_average: Double,
    @Json(name = "vote_count")
    val vote_count: Int,
    @Json(name = "width")
    val width: Int
)