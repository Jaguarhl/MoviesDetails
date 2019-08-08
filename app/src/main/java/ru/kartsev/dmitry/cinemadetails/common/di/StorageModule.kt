package ru.kartsev.dmitry.cinemadetails.common.di

import androidx.room.Room
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.MovieDatabase
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage

object StorageModule {
    /** Section: Constants. */
    const val MOVIE_DATABASE_NAME = "storage.movie_database"
    const val CONFIGURATION_STORAGE_NAME = "storage.configuration_storage"
    const val LANGUAGE_STORAGE_NAME = "storage.language_storage"
    const val MOVIE_DETAILS_STORAGE_NAME = "storage.movie_details_storage"
    const val CONFIGURATION_STORAGE_DAO = "storage.configuration_dao"
    const val LANGUAGES_STORAGE_DAO = "storage.languages_dao"
    const val MOVIE_DETAILS_STORAGE_DAO = "storage.movie_details_dao"
    const val MOVIE_GENRES_STORAGE_DAO = "storage.movie_genres_dao"
    const val MOVIE_VIDEOS_STORAGE_DAO = "storage.movie_videos_dao"

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

        single(named(CONFIGURATION_STORAGE_DAO)) {
            get<MovieDatabase>().configurationDao()
        }

        single(named(LANGUAGES_STORAGE_DAO)) {
            get<MovieDatabase>().languagesDao()
        }

        single(named(MOVIE_DETAILS_STORAGE_DAO)) {
            get<MovieDatabase>().movieDetailsDao()
        }

        single(named(MOVIE_GENRES_STORAGE_DAO)) {
            get<MovieDatabase>().genresDao()
        }

        single(named(MOVIE_VIDEOS_STORAGE_DAO)) {
            get<MovieDatabase>().movieVideosDao()
        }
    }
}