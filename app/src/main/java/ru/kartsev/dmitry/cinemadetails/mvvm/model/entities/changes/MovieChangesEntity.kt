package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.changes


import com.squareup.moshi.Json

data class MovieChangesEntity(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Changed>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)