package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DiffItemObservable

data class SimilarMovieObservable(
    val id: Int,
    val title: String,
    val image: String,
    val voteAverage: String
): DiffItemObservable