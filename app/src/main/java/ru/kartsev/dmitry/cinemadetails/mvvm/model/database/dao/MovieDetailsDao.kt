package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData.Companion.ID_FIELD
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData.Companion.TABLE_NAME

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(language: MovieDetailsData): Long

    @Query("SELECT * FROM $TABLE_NAME WHERE $ID_FIELD LIKE :movieId")
    suspend fun loadById(movieId: Int): MovieDetailsData?

    @Query(
        """
        DELETE FROM $TABLE_NAME
        WHERE $ID_FIELD LIKE :movieId;
        """
    )
    fun deleteById(movieId: Int)

    @Query("SELECT * FROM $TABLE_NAME;")
    fun getAll(): List<MovieDetailsData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()

}