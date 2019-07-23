package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData.Companion.TABLE_NAME

@Dao
interface GenresDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(language: GenreData): Long

    @Insert(onConflict = REPLACE)
    suspend fun save(list: List<GenreData>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME;")
    suspend fun load(): List<GenreData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}