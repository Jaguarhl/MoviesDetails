package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import androidx.paging.PositionalDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import timber.log.Timber
import java.lang.Exception

class MoviesDataSource(
    private val movieRepository: MovieRepository,
    private val configurationRepository: TmdbSettingsRepository
) : PositionalDataSource<MovieObservable>(), KoinComponent {

    /** Section: Constants. */

    companion object {
        private const val INITIAL_PAGE = 1
    }

    /** Section: Common Methods. */

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MovieObservable>
    ) {
        GlobalScope.launch {
            try {
                val response = movieRepository.getNowPlayingMovie(
                    INITIAL_PAGE,
                    configurationRepository.currentLanguage
                )
                val list = convertToObservable(response?.results)
                val count = response?.total_pages ?: 0
                Timber.d(
                    "Data fetched. Initial position: ${params.requestedStartPosition}, total pages count: $count, data loaded: $list"
                )
                callback.onResult(list.orEmpty(), params.requestedStartPosition, count)
            } catch (exception: Exception) {
                Timber.w(exception)
                callback.onResult(emptyList(), params.requestedStartPosition, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieObservable>) {
        GlobalScope.launch {
            try {

                val response =
                    movieRepository.getNowPlayingMovie(
                        params.startPosition,
                        configurationRepository.currentLanguage
                    )
                val list = convertToObservable(response?.results)
                callback.onResult(list.orEmpty())
            } catch (exception: Exception) {
                Timber.w(exception)
            }
        }
    }

    /** Section: Private Methods. */

    private fun convertToObservable(response: List<MovieEntity>?): List<MovieObservable>? {
        return response?.map {
            MovieObservable(
                it.id,
                it.vote_average.toString(),
                it.title,
                it.overview,
                it.poster_path ?: "",
                it.backdrop_path ?: "",
                it.adult,
                it.release_date
            )
        }
    }
}