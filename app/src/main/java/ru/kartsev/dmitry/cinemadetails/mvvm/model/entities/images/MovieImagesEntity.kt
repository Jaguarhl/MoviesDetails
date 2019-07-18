package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.images


import com.squareup.moshi.Json

data class MovieImagesEntity(
    @Json(name = "backdrops")
    val backdrops: List<Backdrop>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "posters")
    val posters: List<Poster>
)