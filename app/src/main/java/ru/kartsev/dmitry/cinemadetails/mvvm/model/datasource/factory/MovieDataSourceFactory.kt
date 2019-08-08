package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory

import androidx.paging.DataSource
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.MOVIES_DATASOURCE_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MovieDataSourceFactory : DataSource.Factory<Int, MovieObservable>(), KoinComponent {

    /** Section: Injects. */

    private val moviesDataSource: MoviesDataSource by inject(named(MOVIES_DATASOURCE_NAME))

    override fun create(): DataSource<Int, MovieObservable> {
        return moviesDataSource
    }
}