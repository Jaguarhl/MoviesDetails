package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.databinding.Bindable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import kotlin.coroutines.CoroutineContext

class MainViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()

    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    /** Section: Simple Properties. */

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val popularMovies = mutableListOf<MovieObservable>()

    var totalPagesCount: Int = 0
    var totalResultsCount: Int = 0
    var currentPage: Int = 1
    val listOffset: Int = 20
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    /** Section: Initialization. */

    fun initializeByDefault() {
        fetchMovies(1)
    }

    /** Section: Common Methods. */

    fun fetchMoreMovies() {
        fetchMovies(currentPage)
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    /** Section: Private Methods. */

    private fun fetchMovies(pageToDisplay: Int) {
        scope.launch {
            if (currentPage == totalPagesCount && totalPagesCount > 0) return@launch

            val serverAnswer = movieRepository.getPopularMovies(pageToDisplay) ?: return@launch

            with(serverAnswer) {
                totalResultsCount = total_results
                currentPage = page
                totalPagesCount = total_pages
                val observablesList = results.map {
                    MovieObservable(
                        it.id,
                        it.vote_average.toString(),
                        it.title,
                        it.overview,
                        it.posterPath ?: "",
                        it.backdoorPath ?: "",
                        it.adult,
                        it.releaseDate ?: ""
                    )
                }

                withContext(Dispatchers.Main) {
                    popularMovies.addAll(observablesList)

                    action = ACTION_DISPLAY_RESULTS
                }
            }
        }
    }

    companion object {
        const val ACTION_DISPLAY_RESULTS = 0
    }
}