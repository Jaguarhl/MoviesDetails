package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DiffItemObservable

data class ImageObservable(
    val imagePath: String,
    val voteAverage: String,
    val width: String,
    val height: String
) : DiffItemObservable