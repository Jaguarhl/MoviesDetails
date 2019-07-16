package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.similar

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity

data class SimilarMoviesEntity(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MovieEntity>,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "total_results")
    val total_results: Int
)