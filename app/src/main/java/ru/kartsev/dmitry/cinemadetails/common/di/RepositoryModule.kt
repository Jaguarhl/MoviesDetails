package ru.kartsev.dmitry.cinemadetails.common.di

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository

object RepositoryModule {
    private const val MOVIES_REPOSITORY_NAME = "repository.movies"
    private const val MOVIES_DATASOURCE_NAME = "repository.movies_datasource"
    private const val MOVIES_DATASOURCE_FACTORY_NAME = "repository.movies_datasource_factory"

    val it : Module = module {
        single(MOVIES_REPOSITORY_NAME) {
            MovieRepository()
        }

        single(MOVIES_DATASOURCE_NAME) {
            MoviesDataSource()
        }

        single(MOVIES_DATASOURCE_FACTORY_NAME) {
            MovieDataSourceFactory()
        }
    }
}