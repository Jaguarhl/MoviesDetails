package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.changes


import com.squareup.moshi.Json

data class Changed(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "id")
    val id: Int
)