package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper.DiffItemObservable

data class VideoObservable(
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
): DiffItemObservable