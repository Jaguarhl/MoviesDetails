package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import androidx.paging.PositionalDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MoviesDataSource : PositionalDataSource<MovieObservable>(), KoinComponent {
    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()

    /** Section: Common Methods. */

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MovieObservable>
    ) {
        GlobalScope.launch {
            val response = movieRepository.getPopularMovies(params.requestedStartPosition)
            val list = convertToObservable(response)
            val count = response?.total_pages ?: 0
            callback.onResult(list ?: listOf(), params.requestedStartPosition, count)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieObservable>) {
        GlobalScope.launch {
            val response = movieRepository.getPopularMovies(params.startPosition)
            val list = convertToObservable(response)
            callback.onResult(list ?: listOf())
        }
    }

    /** Section: Private Methods. */

    private fun convertToObservable(response: TmdbMovieResponseEntity?): List<MovieObservable>? {
        return response?.results?.map {
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
    }
}