package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData.Companion.MOVIE_ID_FIELD
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData.Companion.TABLE_NAME

@Dao
interface MovieVideosDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(video: MovieVideoData): Long

    @Insert(onConflict = REPLACE)
    suspend fun save(list: List<MovieVideoData>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME WHERE $MOVIE_ID_FIELD LIKE :movieId;")
    suspend fun loadByMovieId(movieId: Int): List<MovieVideoData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}