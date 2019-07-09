package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular


import com.squareup.moshi.Json

data class PopularMoviesEntity(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MovieEntity>,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "total_results")
    val total_results: Int
)