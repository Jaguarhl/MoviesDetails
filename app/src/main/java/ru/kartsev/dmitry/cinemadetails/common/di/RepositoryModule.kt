package ru.kartsev.dmitry.cinemadetails.common.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig.SETTINGS_REPOSITORY_LIFETIME_H
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.converter.MovieItemConverter
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.CacheStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.FavouritesStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieListStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MovieDataSourceFactory
import ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource.MoviesDataSource
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.FavouritesRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(
        util: Util,
        settingsApi: SettingsApi,
        configurationStorage: ConfigurationStorage,
        languageStorage: LanguageStorage,
        movieDetailsStorage: MovieDetailsStorage
    ) = TmdbSettingsRepository(
        SETTINGS_REPOSITORY_LIFETIME_H,
        util,
        settingsApi,
        configurationStorage,
        languageStorage,
        movieDetailsStorage
    )

    @Provides
    @Singleton
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        movieDetailsStorage: MovieDetailsStorage,
        movieListStorage: MovieListStorage,
        cacheStorage: CacheStorage,
        settingsRepository: TmdbSettingsRepository,
        moshi: Moshi,
        util: Util
    ) = MovieRepository(
        moviesApi,
        movieDetailsStorage,
        movieListStorage,
        cacheStorage,
        settingsRepository,
        moshi,
        util
    )

    @Provides
    @Singleton
    fun provideFavouritesRepository(favouritesStorage: FavouritesStorage) =
        FavouritesRepository(favouritesStorage)

    @Provides
    fun provideMoviesDataSource(
        movieRepository: MovieRepository,
        configurationRepository: TmdbSettingsRepository,
        converter: MovieItemConverter) = MoviesDataSource(
        movieRepository,
        configurationRepository,
        converter
    )

    @Provides
    @Singleton
    fun provideMovieDataSourceFactory() = MovieDataSourceFactory()
}