package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base

abstract class MovieListBaseViewModel : BaseViewModel() {

    /** Section: Simple Properties. */

    var moviePosterSize: String? = null

    /** Section: Abstract Methods. */

    abstract fun movieItemClicked(id: Int)

}