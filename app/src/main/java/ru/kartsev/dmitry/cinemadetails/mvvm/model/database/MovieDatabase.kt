package ru.kartsev.dmitry.cinemadetails.mvvm.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.ConfigurationDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.FavouritesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.GenresDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.LanguagesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieDetailsDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieVideosDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.ConfigurationData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData

@Database(
    entities = [
        ConfigurationData::class, LanguageData::class, MovieDetailsData::class, GenreData::class,
        MovieVideoData::class, FavouriteData::class
    ],
    version = StorageConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
    abstract fun languagesDao(): LanguagesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun genresDao(): GenresDao
    abstract fun movieVideosDao(): MovieVideosDao
    abstract fun favouritesDao(): FavouritesDao
}