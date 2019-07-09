package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()

    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    var loading: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    var movieTitle: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieTitle)
        }

    var movieDescription: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieDescription)
        }

    /** Section: Simple Properties. */

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /** Section: Initialization. */

    fun initializeWithMovieId(id: Int) {
        scope.launch {
            loadMovieData(id)
        }.invokeOnCompletion {
            Log.d(this@MovieDetailsViewModel::class.java.simpleName, movieTitle)

        }
    }

    private suspend fun loadMovieData(id: Int) {
        loading = true
        val resultDetails = movieRepository.getMovieDetails(id)

        // FIXME: Move language param to variable. WTF? iso codes come as null?
        val translationDetails = movieRepository.getMovieTranslations(id)?.translations?.
            first { it.iso_639_1.equals("ru", true) }?.data

        withContext(Dispatchers.Main) {
            movieTitle = translationDetails?.title ?: resultDetails?.title ?: ""
            movieDescription = translationDetails?.overview ?: resultDetails?.overview ?: ""
            Log.d(this@MovieDetailsViewModel::class.java.simpleName, translationDetails.toString())

            loading = false
        }
    }
}