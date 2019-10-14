package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.converter.MovieItemConverter
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import timber.log.Timber
import java.lang.Exception

class MoviesDataSource(
    private val movieRepository: MovieRepository,
    private val configurationRepository: TmdbSettingsRepository,
    private val movieToObservableConverter: MovieItemConverter
) : PositionalDataSource<MovieObservable>(), KoinComponent {

    /** Section: Constants. */

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private val networkState = MutableLiveData<NetworkState>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        networkState.postValue(NetworkState(NetworkState.Status.FAILED, exception.toString()))
    }

    /** Section: Common Methods. */

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MovieObservable>
    ) {
        GlobalScope.launch(coroutineExceptionHandler) {
            networkState.postValue(NetworkState(NetworkState.Status.RUNNING))
            try {
                val response = movieRepository.getNowPlayingMovie(
                    INITIAL_PAGE,
                    configurationRepository.currentLanguage
                )
                val list = movieToObservableConverter.convertToObservable(response.results)
                val count = response?.total_pages ?: 0
                Timber.d(
                    "Data fetched. Initial position: ${params.requestedStartPosition}, total pages count: $count, data loaded: $list"
                )
                callback.onResult(list.orEmpty(), params.requestedStartPosition, count)
                networkState.postValue(NetworkState(NetworkState.Status.SUCCESS))
            } catch (exception: Exception) {
                Timber.w(exception)
                callback.onResult(emptyList(), params.requestedStartPosition, 0)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, exception.toString()))
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieObservable>) {
        GlobalScope.launch(coroutineExceptionHandler) {
            networkState.postValue(NetworkState(NetworkState.Status.RUNNING))
            try {
                val response =
                    movieRepository.getNowPlayingMovie(
                        params.startPosition,
                        configurationRepository.currentLanguage
                    )
                val list = movieToObservableConverter.convertToObservable(response?.results)
                callback.onResult(list.orEmpty())
                networkState.postValue(NetworkState(NetworkState.Status.SUCCESS))
            } catch (exception: Exception) {
                Timber.w(exception)
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, exception.toString()))
            }
        }
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState
}