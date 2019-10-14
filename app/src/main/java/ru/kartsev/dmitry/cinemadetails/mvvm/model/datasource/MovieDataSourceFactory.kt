package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import org.koin.core.KoinComponent
import org.koin.core.get
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MovieDataSourceFactory : DataSource.Factory<Int, MovieObservable>(), KoinComponent {

    /** Section: Simple Properties. */

    var moviesLiveData: MutableLiveData<MoviesDataSource>? = null

    override fun create(): DataSource<Int, MovieObservable> {
        val moviesDataSource = get<MoviesDataSource>()

        moviesLiveData = MutableLiveData()
        moviesLiveData!!.postValue(moviesDataSource)

        return moviesDataSource
    }
}