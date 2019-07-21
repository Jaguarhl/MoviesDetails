package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import android.util.Log
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.LANGUAGE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import java.lang.Exception

class MoviesDataSource : PositionalDataSource<MovieObservable>(), KoinComponent {

    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()

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
                val response = movieRepository.getNowPlayingMovie(INITIAL_PAGE, LANGUAGE)
                val list = convertToObservable(response?.results)
                val count = response?.total_pages ?: 0
                Log.d(
                    this@MoviesDataSource.javaClass.canonicalName, "Data fetched. Initial position: " +
                        "${params.requestedStartPosition}, total pages count: $count, data loaded: $list"
                )
//                callback.onResult(list ?: listOf(), params.requestedStartPosition, count)
                callback.onResult(list ?: listOf(), params.requestedStartPosition)
            } catch (exception: Exception) {
                Log.w(this@MoviesDataSource.javaClass.canonicalName, "Failed to fetch initial data", exception)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieObservable>) {
        GlobalScope.launch {
            try {

                val response = movieRepository.getNowPlayingMovie(params.startPosition, LANGUAGE)
                val list = convertToObservable(response?.results)
                callback.onResult(list ?: listOf())
            } catch (exception: Exception) {
                Log.w(this@MoviesDataSource.javaClass.canonicalName, "Failed to fetch range data", exception)
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
                it.poster_path,
                it.backdrop_path ?: "",
                it.adult,
                it.release_date
            )
        }
    }
}