package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities

data class TmdbMovieResponseEntity(
    val page: Int,
    val results: List<MovieEntity>,
    val total_results: Int,
    val total_pages: Int
)