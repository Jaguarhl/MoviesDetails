package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class MovieListBaseViewModel : BaseViewModel() {

    /** Section: Bindable Properties. */

    val moviesListEmpty = MutableLiveData<Boolean>().apply { value = false }

    /** Section: Simple Properties. */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)

    var moviePosterSize: String? = null

    /** Section: Abstract Methods. */

    abstract fun movieItemClicked(id: Int)

}