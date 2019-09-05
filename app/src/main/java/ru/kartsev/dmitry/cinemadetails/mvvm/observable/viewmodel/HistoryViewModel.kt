package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.BaseViewModel

class HistoryViewModel(
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    /** Section: Initialization. */

    fun initializeByDefault() {

    }
}