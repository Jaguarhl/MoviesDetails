package ru.kartsev.dmitry.cinemadetails.mvvm.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kartsev.dmitry.cinemadetails.common.config.StorageConfig
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.ConfigurationDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.ConfigurationData

@Database(
    entities = [
        ConfigurationData::class
    ],
    version = StorageConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
}