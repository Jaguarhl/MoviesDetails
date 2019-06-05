package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory

import androidx.paging.DataSource
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MovieDataSourceFactory : DataSource.Factory<Int, MovieObservable>(), KoinComponent {

    /** Section: Injects. */

    private val moviesDataSource: MoviesDataSource by inject()

    override fun create(): DataSource<Int, MovieObservable> {
        return moviesDataSource
    }
}