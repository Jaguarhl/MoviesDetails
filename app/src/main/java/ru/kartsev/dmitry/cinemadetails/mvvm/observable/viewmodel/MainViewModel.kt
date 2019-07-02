package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.PAGE_SIZE
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieDetailsObservable
import rx.functions.Func1

class MainViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieDataSourceFactory: MovieDataSourceFactory by inject()
    private val movieRepository: MovieRepository by inject()
    private val compositeDisposable: CompositeDisposable by inject()

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

        movieRepository.getMovieDetails(id)
            .map { MovieDetailsObservable.fromMovieDetailsEntity(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MovieDetailsObservable> {
                override fun onSuccess(movie: MovieDetailsObservable) {
                    Log.d(this@MainViewModel::class.java.canonicalName, movie.toString())
                    action = ACTION_OPEN_DETAILS
                }

                override fun onSubscribe(d: Disposable) { }

                override fun onError(e: Throwable) {
                    Log.e(this@MainViewModel::class.java.canonicalName, "Error occur: ", e)
                }
            })
    }

    /** Section: Private Methods. */

    private fun initializedPagedListBuilder(config: PagedList.Config):
        LivePagedListBuilder<Int, MovieObservable> {

        return LivePagedListBuilder(movieDataSourceFactory, config)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val ACTION_OPEN_DETAILS = 0
    }
}