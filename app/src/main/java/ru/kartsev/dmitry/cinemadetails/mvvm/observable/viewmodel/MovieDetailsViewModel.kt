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
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.LANGUAGE
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

    var movieTitleOriginal: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieTitleOriginal)
        }

    var movieDescription: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieDescription)
        }

    var moviePosterPath: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.moviePosterPath)
        }

    var movieBackdropPath: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieBackdropPath)
        }

    var movieReleaseDate: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieReleaseDate)
        }

    /** Section: Simple Properties. */

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    var movieBackdropSize: String = ""

    /** Section: Initialization. */

    fun initializeWithMovieId(id: Int) {
        // FIXME: Set it from settings repository.
        movieBackdropSize = "w780"
        scope.launch {
            loadMovieData(id)
        }.invokeOnCompletion {
            Log.d(this@MovieDetailsViewModel::class.java.simpleName, movieTitle)

        }
    }

    private suspend fun loadMovieData(id: Int) {
        loading = true
        val resultDetails = movieRepository.getMovieDetails(id, LANGUAGE)

        // FIXME: Move language param to variable.
        val translationDetails = movieRepository.getMovieTranslations(id)?.translations?.
            first { it.iso_639_1.equals("ru", true) }?.data

        withContext(Dispatchers.Main) {
            movieTitle = translationDetails?.title ?: resultDetails?.title ?: ""
            movieTitleOriginal = "(${resultDetails?.original_title ?: ""})"
            movieDescription = translationDetails?.overview ?: resultDetails?.overview ?: ""
            moviePosterPath = resultDetails?.poster_path ?: ""
            movieBackdropPath = resultDetails?.backdrop_path ?: ""
            movieReleaseDate = resultDetails?.release_date ?: ""
            Log.d(this@MovieDetailsViewModel::class.java.simpleName, translationDetails.toString())

            loading = false
        }
    }
}