package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.PAGE_SIZE
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.MovieListBaseViewModel

class NowPlayingViewModel(
    private val movieDataSourceFactory: MovieDataSourceFactory,
    private val settingsRepository: TmdbSettingsRepository
) : MovieListBaseViewModel() {
    /** Section: Simple Properties. */

    lateinit var nowPlayingMovies: LiveData<PagedList<MovieObservable>>

    var movieIdToOpenDetails: Int? = null

    /** Section: Initialization. */

    fun initializeByDefault() {

        loading = true
        val config = Config.Builder().apply {
            setPageSize(PAGE_SIZE)
            setEnablePlaceholders(false)
        }.build()

        nowPlayingMovies = initializedPagedListBuilder(config).build()

        scope.launch(coroutineExceptionHandler) {
            getTmdbSettings()
            moviePosterSize = settingsRepository.posterSizes[0]
        }
    }

    /** Section: Common Methods. */

    override fun movieItemClicked(id: Int) {
        if (id == 0) return

        movieIdToOpenDetails = id
        action = ACTION_OPEN_DETAILS
    }

    fun refreshData() {
        movieDataSourceFactory.moviesLiveData?.value?.invalidate()
    }

    /** Section: Private Methods. */

    private fun getTmdbSettings() {
        runBlocking {
            settingsRepository.getTmdbSettings()
        }
    }

    private fun initializedPagedListBuilder(config: Config):
        LivePagedListBuilder<Int, MovieObservable> {

        return LivePagedListBuilder<Int, MovieObservable>(
            movieDataSourceFactory,
            config
        ).setBoundaryCallback(object : BoundaryCallback<MovieObservable>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                loading = false
                moviesListEmpty.postValue(true)
            }

            override fun onItemAtFrontLoaded(itemAtFront: MovieObservable) {
                super.onItemAtFrontLoaded(itemAtFront)
                loading = false
                moviesListEmpty.postValue(false)
            }
        })
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        moviesListEmpty.postValue(true)
    }

    companion object {
        const val ACTION_OPEN_DETAILS = 0
    }
}