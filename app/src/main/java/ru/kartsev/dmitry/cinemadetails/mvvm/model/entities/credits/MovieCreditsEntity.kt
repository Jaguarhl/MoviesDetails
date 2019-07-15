package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits


import com.squareup.moshi.Json

data class MovieCreditsEntity(
    @Json(name = "cast")
    val cast: List<Cast>,
    @Json(name = "crew")
    val crew: List<Crew>,
    @Json(name = "id")
    val id: Int
)