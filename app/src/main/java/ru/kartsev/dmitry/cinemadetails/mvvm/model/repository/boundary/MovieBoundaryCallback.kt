package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.boundary

import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MovieBoundaryCallback (
    private val coroutineScope: CoroutineScope,
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
    private val movieRepository: MovieRepository,
    private val settingsRepository: TmdbSettingsRepository
) : PagedList.BoundaryCallback<MovieObservable>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        coroutineScope.launch(coroutineExceptionHandler) {
            movieRepository.getNowPlayingMovie(0, settingsRepository.currentLanguage)
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: MovieObservable) {
        super.onItemAtFrontLoaded(itemAtFront)
        coroutineScope.launch(coroutineExceptionHandler) {
            movieRepository.getNowPlayingMovie(itemAtFront, settingsRepository.currentLanguage)
        }
    }
}