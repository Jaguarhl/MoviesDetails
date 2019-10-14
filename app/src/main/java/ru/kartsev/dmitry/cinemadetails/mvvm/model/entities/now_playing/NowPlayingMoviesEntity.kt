package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.now_playing

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieData

data class NowPlayingMoviesEntity(
    @Json(name = "dates")
    val dates: Dates,
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MovieData>,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "total_results")
    val total_results: Int
)