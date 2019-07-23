package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.ConfigurationData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.ConfigurationData.Companion.TABLE_NAME

@Dao
interface ConfigurationDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(data: ConfigurationData): Long

    @Query(value = "SELECT * FROM $TABLE_NAME;")
    suspend fun configuration(): ConfigurationData

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}