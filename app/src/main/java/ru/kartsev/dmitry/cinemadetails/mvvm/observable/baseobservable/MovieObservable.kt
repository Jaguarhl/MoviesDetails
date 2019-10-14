package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper.DiffItemObservable

data class MovieObservable(
    val id: Int,
    val vote_average: String = "",
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdoorPath: String = "",
    val adult: Boolean = false,
    val releaseDate: String = ""
): DiffItemObservable {
    val isPosterVisible = posterPath.isNotEmpty()
}