package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base

import androidx.lifecycle.MutableLiveData

abstract class MovieListBaseViewModel : BaseViewModel() {

    /** Section: Bindable Properties. */

    val moviesListEmpty = MutableLiveData<Boolean>().apply { value = false }

    /** Section: Simple Properties. */

    var moviePosterSize: String? = null

    /** Section: Abstract Methods. */

    abstract fun movieItemClicked(id: Int)

}