package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import android.util.Log
import androidx.paging.PositionalDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MoviesDataSource : PositionalDataSource<MovieObservable>(), KoinComponent {

    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()
    private val compositeDisposable: CompositeDisposable by inject()

    /** Section: Constants. */

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable -> Log.e(this.javaClass.canonicalName, throwable.message) })
            )
        }
    }

    /** Section: Common Methods. */

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MovieObservable>
    ) {
        compositeDisposable.add(movieRepository.getPopularMovies(INITIAL_PAGE).subscribe({ movies ->
            setRetry(null)
            callback.onResult(
                convertToObservable(movies) ?: listOf(),
                params.requestedStartPosition,
                movies?.total_pages ?: 0
            )
        }, { throwable ->
            setRetry(Action { loadInitial(params, callback) })
        }))
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieObservable>) {
        compositeDisposable.add(
            movieRepository.getPopularMovies(params.startPosition).subscribe({ result ->
                callback.onResult(convertToObservable(result) ?: listOf())
            }, { throwable -> })
        )
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

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}