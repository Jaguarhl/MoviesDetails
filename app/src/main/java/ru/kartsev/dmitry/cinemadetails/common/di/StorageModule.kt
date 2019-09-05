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
    private const val MOVIE_DATABASE_NAME = "storage.movie_database"

    /** Section: Modules. */

    val it: Module = module {
        single(named(MOVIE_DATABASE_NAME)) {
            Room.databaseBuilder(
                get(), MovieDatabase::class.java,
                StorageConfig.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }

        single {
            ConfigurationStorage(get())
        }

        single {
            LanguageStorage(get())
        }

        single {
            MovieDetailsStorage(get(), get(), get())
        }

        single {
            FavouritesStorage(get())
        }

        single {
            CacheStorage(get())
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).configurationDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).languagesDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).movieDetailsDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).genresDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).movieVideosDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).favouritesDao()
        }

        single {
            get<MovieDatabase>(named(MOVIE_DATABASE_NAME)).cacheDao()
        }
    }
}