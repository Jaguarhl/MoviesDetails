package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities

data class MovieEntity(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdoorPath: String?,
    val adult: Boolean,
    val releaseDate: String?
)