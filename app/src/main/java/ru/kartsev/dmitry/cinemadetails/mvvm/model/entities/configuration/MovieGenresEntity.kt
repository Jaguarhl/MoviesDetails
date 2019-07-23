package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData

data class MovieGenresEntity(
    @Json(name = "genres")
    val genres: List<GenreData>
)