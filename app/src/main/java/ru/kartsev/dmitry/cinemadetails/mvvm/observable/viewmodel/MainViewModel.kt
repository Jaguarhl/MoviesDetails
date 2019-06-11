package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import kotlin.coroutines.CoroutineContext

class MainViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieDataSource: MoviesDataSource by inject()

    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    /** Section: Simple Properties. */

    private var popularMovies: LiveData<PagedList<MovieObservable>>

    /** Section: Initialization. */

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        popularMovies  = initializedPagedListBuilder(config).build()
    }

    /** Section: Common Methods. */

    fun getMoviesList(): LiveData<PagedList<MovieObservable>> = popularMovies

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

        val dataSourceFactory = object : DataSource.Factory<Int, MovieObservable>() {
            override fun create(): DataSource<Int, MovieObservable> {
                return movieDataSource
            }
        }

        return LivePagedListBuilder<Int, MovieObservable>(dataSourceFactory, config)
    }
}