package ru.kartsev.dmitry.cinemadetails.common.di

import androidx.room.Room
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.MovieDatabase
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage

object StorageModule {
    /** Section: Constants. */
    const val MOVIE_DATABASE_NAME = "storage.movie_database"
    const val CONFIGURATION_STORAGE_NAME = "storage.configuration_storage"
    const val LANGUAGE_STORAGE_NAME = "storage.language_storage"

    const val CONFIGURATION_STORAGE_DAO = "storage.configuration_dao"
    const val LANGUAGES_STORAGE_DAO = "storage.languages_dao"

    /** Section: Modules. */

    val it: Module = module {
        single(MOVIE_DATABASE_NAME) {
            Room.databaseBuilder(
                get(), MovieDatabase::class.java,
                StorageConfig.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }

        single(CONFIGURATION_STORAGE_NAME) {
            ConfigurationStorage()
        }

        single(LANGUAGE_STORAGE_NAME) {
            LanguageStorage()
        }

        single(CONFIGURATION_STORAGE_DAO) {
            get<MovieDatabase>().configurationDao()
        }

        single(LANGUAGES_STORAGE_DAO) {
            get<MovieDatabase>().languagesDao()
        }
    }
}