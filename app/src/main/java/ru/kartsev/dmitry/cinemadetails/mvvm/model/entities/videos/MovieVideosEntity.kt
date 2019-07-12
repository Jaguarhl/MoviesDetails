package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos

import com.squareup.moshi.Json

data class MovieVideosEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<MovieVideo>
)