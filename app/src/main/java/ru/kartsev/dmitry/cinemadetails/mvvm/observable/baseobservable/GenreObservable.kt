package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper.DiffItemObservable

data class GenreObservable(
    var id: Int,
    var name: String = ""
): DiffItemObservable