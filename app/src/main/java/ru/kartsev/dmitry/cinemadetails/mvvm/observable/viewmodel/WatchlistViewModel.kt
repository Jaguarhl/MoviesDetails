package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.runBlocking
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.PAGE_SIZE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.MovieListBaseViewModel

class WatchlistViewModel : MovieListBaseViewModel() {
    /** Section: Injections. */

    private val movieDataSourceFactory: MovieDataSourceFactory by inject()
    private val settingsRepository: TmdbSettingsRepository by inject()

    /** Section: Simple Properties. */

    var comingSoonMovies: LiveData<PagedList<MovieObservable>>

    var movieIdToOpenDetails: Int? = null

    /** Section: Initialization. */

    init {
        getTmdbSettings()
        moviePosterSize = settingsRepository.posterSizes[0]
        val config = PagedList.Config.Builder().apply {
            setPageSize(PAGE_SIZE)
            setEnablePlaceholders(false)
        }.build()

        comingSoonMovies = initializedPagedListBuilder(config).build()
    }

    /** Section: Common Methods. */

    override fun movieItemClicked(id: Int) {
        if (id == 0) return

        movieIdToOpenDetails = id
        action = ACTION_OPEN_DETAILS
    }

    /** Section: Private Methods. */

    private fun getTmdbSettings() {
        runBlocking {
            settingsRepository.getTmdbSettings()
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
        LivePagedListBuilder<Int, MovieObservable> {

        return LivePagedListBuilder<Int, MovieObservable>(movieDataSourceFactory, config)
    }

    companion object {
        const val ACTION_OPEN_DETAILS = 0
    }
}