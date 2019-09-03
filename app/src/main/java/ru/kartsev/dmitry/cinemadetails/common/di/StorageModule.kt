package ru.kartsev.dmitry.cinemadetails.common.di

import androidx.room.Room
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.MovieDatabase
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.CacheStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.FavouritesStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage

object StorageModule {
    /** Section: Constants. */
    const val MOVIE_DATABASE_NAME = "storage.movie_database"
    const val CONFIGURATION_STORAGE_NAME = "storage.configuration_storage"
    const val LANGUAGE_STORAGE_NAME = "storage.language_storage"
    const val MOVIE_DETAILS_STORAGE_NAME = "storage.movie_details_storage"
    const val FAVOURITES_STORAGE_NAME = "storage.favourites_storage"
    const val CACHE_STORAGE_NAME = "storage.cache_storage"
    const val CONFIGURATION_STORAGE_DAO = "storage.configuration_dao"
    const val LANGUAGES_STORAGE_DAO = "storage.languages_dao"
    const val MOVIE_DETAILS_STORAGE_DAO = "storage.movie_details_dao"
    const val MOVIE_GENRES_STORAGE_DAO = "storage.movie_genres_dao"
    const val MOVIE_VIDEOS_STORAGE_DAO = "storage.movie_videos_dao"
    const val FAVOURITES_STORAGE_DAO = "storage.favourites_dao"
    private const val CACHE_STORAGE_DAO = "storage.cache_dao"

    /** Section: Modules. */

    val it: Module = module {
        single(named(MOVIE_DATABASE_NAME)) {
            Room.databaseBuilder(
                get(), MovieDatabase::class.java,
                StorageConfig.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }

        single(named(CONFIGURATION_STORAGE_NAME)) {
            ConfigurationStorage()
        }

        single(named(LANGUAGE_STORAGE_NAME)) {
            LanguageStorage()
        }

        single(named(MOVIE_DETAILS_STORAGE_NAME)) {
            MovieDetailsStorage()
        }

        single(named(FAVOURITES_STORAGE_NAME)) {
            FavouritesStorage()
        }

        single(named(CACHE_STORAGE_NAME)) {
            CacheStorage(get(named(CACHE_STORAGE_DAO)))
        }

        single(named(CONFIGURATION_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).configurationDao()
        }

        single(named(LANGUAGES_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).languagesDao()
        }

        single(named(MOVIE_DETAILS_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).movieDetailsDao()
        }

        single(named(MOVIE_GENRES_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).genresDao()
        }

        single(named(MOVIE_VIDEOS_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).movieVideosDao()
        }

        single(named(FAVOURITES_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).favouritesDao()
        }

        single(named(CACHE_STORAGE_DAO)) {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).cacheDao()
        }
    }
}