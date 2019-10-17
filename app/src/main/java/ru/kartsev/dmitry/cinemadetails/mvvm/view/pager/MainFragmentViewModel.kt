package ru.kartsev.dmitry.cinemadetails.mvvm.view.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.PAGE_SIZE
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.FavouritesRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieListBaseViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.SingleLiveEvent

class MainFragmentViewModel : MovieListBaseViewModel() {

    /** Section: Injections. */

    private val movieDataSourceFactory: MovieDataSourceFactory by inject()
    private val favouritesRepository: FavouritesRepository by inject()
    private val movieRepository: MovieRepository by inject()
    private val settingsRepository: TmdbSettingsRepository by inject()

    /** Section: Simple Properties. */

    lateinit var nowPlayingMovies: LiveData<PagedList<MovieObservable>>

    val movieUIEvents = SingleLiveEvent<MovieUIEventState>()
    val watchlistMovies: MutableLiveData<List<MovieObservable>> = MutableLiveData()

    /** Section: Initialization. */

    fun initializeByDefault() {
        scope.launch(coroutineExceptionHandler) {
            getTmdbSettings()
            moviePosterSize = settingsRepository.posterSizes[0]
        }
    }

    fun initializeNowPlaying() {
        loading = true
        val config = Config.Builder().apply {
            setPageSize(PAGE_SIZE)
            setEnablePlaceholders(false)
        }.build()

        nowPlayingMovies = initializedPagedListBuilder(config).build()
    }

    fun initializeWatchlist() {
        loadFavourites()
    }

    /** Section: Common Methods. */

    override fun movieItemClicked(id: Int) {
        if (id == 0) return

        movieUIEvents.postValue(ShowMovieDetails(id))
    }

    fun refreshData() {
        movieDataSourceFactory.moviesLiveData?.value?.invalidate()
    }

    fun loadFavourites() {
        scope.launch(coroutineExceptionHandler) {
            loading = true
            val language = settingsRepository.currentLanguage
            val moviesList: MutableList<MovieDetailsEntity> = mutableListOf()
            val favourites = favouritesRepository.getFavouritesList()?.map { it.itemId }
            favourites?.let { ids ->
                movieRepository.getMovieDetailsList(ids, language)?.let { moviesList.addAll(it) }
            }

            watchlistMovies.postValue(
                moviesList.map {
                    MovieObservable(
                        it.id!!,
                        it.vote_average.toString(),
                        it.title ?: it.original_title ?: "",
                        it.overview ?: "",
                        it.poster_path ?: "",
                        it.backdrop_path ?: "",
                        it.adult ?: false,
                        it.release_date ?: ""
                    )
                }
            )

            moviesListEmpty.postValue(moviesList.isEmpty())
            loading = false
        }
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
}