package ru.kartsev.dmitry.cinemadetails.common.di

import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig.SETTINGS_REPOSITORY_LIFETIME_H
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule.API_SETTINGS
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.FavouritesRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository

object RepositoryModule {
    val it: Module = module {

        single {
            TmdbSettingsRepository(
                SETTINGS_REPOSITORY_LIFETIME_H, get(), get(), get(), get(), get()
            )
        }

        single {
            MovieRepository(
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }

        single {
            FavouritesRepository(get())
        }

        factory {
            MoviesDataSource(get(), get())
        }

        single {
            MovieDataSourceFactory()
        }
    }
}