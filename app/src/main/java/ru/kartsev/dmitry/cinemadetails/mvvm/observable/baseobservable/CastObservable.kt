package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper.DiffItemObservable

data class CastObservable(
    val name: String,
    val creditId: String,
    val castId: Int,
    val profilePath: String,
    val imageSize: String = "w185" //FIXME: Move this value to view model
): DiffItemObservable