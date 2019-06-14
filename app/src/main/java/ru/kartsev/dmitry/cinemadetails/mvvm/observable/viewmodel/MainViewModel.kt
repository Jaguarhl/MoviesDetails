package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.PAGE_SIZE
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.Transformations
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory

class MainViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieDataSourceFactory: MovieDataSourceFactory by inject()
    private val movieRepository: MovieRepository by inject()

    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    var loading: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    /** Section: Simple Properties. */

    var popularMovies: LiveData<PagedList<MovieObservable>>

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /** Section: Initialization. */

    init {
        val config = PagedList.Config.Builder().apply {
            setPageSize(PAGE_SIZE)
            setEnablePlaceholders(true)
        }.build()

        popularMovies = initializedPagedListBuilder(config).build()
    }

    /** Section: Common Methods. */

    fun movieItemClicked(id: Int) {
        if (id == 0) return

        scope.launch {
            val result = movieRepository.getMovieDetails(id)
            Log.d(this@MainViewModel::class.java.canonicalName, result.toString())
        }
    }

    /** Section: Private Methods. */

    /*private fun fetchMovies(pageToDisplay: Int) {
        scope.launch {
            if (currentPage == totalPagesCount && totalPagesCount > 0) return@launch

            val serverAnswer = movieRepository.getPopularMovies(pageToDisplay) ?: return@launch

            with(serverAnswer) {
                totalResultsCount = total_results
                currentPage = page
                totalPagesCount = total_pages
                val observablesList = results.map {
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

                withContext(Dispatchers.Main) {
                    popularMovies.addAll(observablesList)

                    action = ACTION_DISPLAY_RESULTS
                }
            }
        }
    }*/

    private fun initializedPagedListBuilder(config: PagedList.Config):
        LivePagedListBuilder<Int, MovieObservable> {

        return LivePagedListBuilder<Int, MovieObservable>(movieDataSourceFactory, config)
    }
}