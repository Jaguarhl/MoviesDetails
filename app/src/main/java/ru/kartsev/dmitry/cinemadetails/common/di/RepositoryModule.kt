package ru.kartsev.dmitry.cinemadetails.common.di

import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.MoshiMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig.MAX_CACHE_SIZE_MB
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig.SETTINGS_REPOSITORY_LIFETIME_H
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.factory.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.FavouritesRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository

object RepositoryModule {
    const val COROUTINES_CACHE_NAME = "repository.coroutines_cache"
    const val TMDB_SETTINGS_REPOSITORY_NAME = "repository.tmdb_settings"
    const val MOVIES_REPOSITORY_NAME = "repository.movies"
    const val MOVIES_DATASOURCE_FACTORY_NAME = "repository.movies_datasource_factory"
    const val FAVOURITES_REPOSITORY_NAME = "repository.favourites"

    val it : Module = module {
        single(named(COROUTINES_CACHE_NAME)) {
            CoroutinesCache(CacheParams(MAX_CACHE_SIZE_MB, MoshiMapper(), androidContext().cacheDir ))
        }

        single(named(TMDB_SETTINGS_REPOSITORY_NAME)) {
            TmdbSettingsRepository(SETTINGS_REPOSITORY_LIFETIME_H)
        }

        single(named(MOVIES_REPOSITORY_NAME)) {
            MovieRepository()
        }

        single(named(FAVOURITES_REPOSITORY_NAME)) {
            FavouritesRepository()
        }

        factory {
            MoviesDataSource()
        }

        single(named(MOVIES_DATASOURCE_FACTORY_NAME)) {
            MovieDataSourceFactory()
        }
    }
}