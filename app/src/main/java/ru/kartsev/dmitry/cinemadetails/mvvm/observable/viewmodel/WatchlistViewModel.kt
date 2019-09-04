package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.FAVOURITES_REPOSITORY_NAME
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.MOVIES_REPOSITORY_NAME
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.TMDB_SETTINGS_REPOSITORY_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.FavouritesRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.MovieListBaseViewModel

class WatchlistViewModel : MovieListBaseViewModel() {
    /** Section: Injections. */

    private val settingsRepository: TmdbSettingsRepository by inject(
        named(
            TMDB_SETTINGS_REPOSITORY_NAME
        )
    )
    private val favouritesRepository: FavouritesRepository by inject(
        named(
            FAVOURITES_REPOSITORY_NAME
        )
    )
    private val movieRepository: MovieRepository by inject(named(MOVIES_REPOSITORY_NAME))

    /** Section: Simple Properties. */

    private var language: String? = settingsRepository.currentLanguage

    val watchlistMovies: MutableLiveData<List<MovieObservable>> = MutableLiveData()

    var movieIdToOpenDetails: Int? = null

    /** Section: Initialization. */

    fun initializeByDefault() {
        getTmdbSettings()
        moviePosterSize = settingsRepository.posterSizes[0]
        loadFavourites()
    }

    fun loadFavourites() {
        scope.launch(coroutineExceptionHandler) {
            loading = true
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

    companion object {
        const val ACTION_OPEN_DETAILS = 0
    }
}