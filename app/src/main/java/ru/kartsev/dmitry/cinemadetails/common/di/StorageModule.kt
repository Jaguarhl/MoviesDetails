package ru.kartsev.dmitry.cinemadetails.common.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.MovieDatabase
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.CacheDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.ConfigurationDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.FavouritesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.GenresDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.HistoryDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.LanguagesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieDetailsDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieVideosDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.CacheStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.FavouritesStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.HistoryStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application) = Room.databaseBuilder(
        application, MovieDatabase::class.java,
        StorageConfig.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideConfigurationStorage(dao: ConfigurationDao) = ConfigurationStorage(dao)

    @Provides
    @Singleton
    fun provideLanguageStorage(dao: LanguagesDao) = LanguageStorage(dao)

    @Provides
    @Singleton
    fun provideMovieDetailsStorage(
        genresDao: GenresDao,
        movieDetailsDao: MovieDetailsDao,
        movieVideosDao: MovieVideosDao
    ) = MovieDetailsStorage(genresDao, movieDetailsDao, movieVideosDao)

    @Provides
    @Singleton
    fun provideFavouritesStorage(dao: FavouritesDao) = FavouritesStorage(dao)

    @Provides
    @Singleton
    fun provideCacheStorage(dao: CacheDao) = CacheStorage(dao)

    @Provides
    @Singleton
    fun provideHistoryStorage(dao: HistoryDao) = HistoryStorage(dao)

    @Provides
    @Singleton
    fun provideConfigyrationDao(database: MovieDatabase) = database.configurationDao()

    @Provides
    @Singleton
    fun provideLanguageDao(database: MovieDatabase) = database.languagesDao()

    @Provides
    @Singleton
    fun provideMovieDetailsDao(database: MovieDatabase) = database.movieDetailsDao()

    @Provides
    @Singleton
    fun provideGenresDao(database: MovieDatabase) = database.genresDao()

    @Provides
    @Singleton
    fun provideMovieVideosDao(database: MovieDatabase) = database.movieVideosDao()

    @Provides
    @Singleton
    fun provideFavouritesDao(database: MovieDatabase) = database.favouritesDao()

    @Provides
    @Singleton
    fun provideCacheDao(database: MovieDatabase) = database.cacheDao()

    @Provides
    @Singleton
    fun provideHistoryDao(database: MovieDatabase) = database.historyDao()
}