package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates

import com.squareup.moshi.Json

data class ReleaseDatesEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<Result>
)