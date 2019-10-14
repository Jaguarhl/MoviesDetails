package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieData.Companion.TABLE_NAME

@Dao
interface MovieListDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(language: MovieData): Long

    @Insert(onConflict = REPLACE)
    suspend fun save(list: List<MovieData>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME;")
    suspend fun load(): DataSource.Factory<Int, MovieData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}