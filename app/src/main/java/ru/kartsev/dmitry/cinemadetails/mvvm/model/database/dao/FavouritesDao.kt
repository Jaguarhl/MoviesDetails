package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData.Companion.ID_FIELD
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData.Companion.TABLE_NAME

@Dao
interface FavouritesDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(data: FavouriteData): Long

    @Query(value = "SELECT * FROM $TABLE_NAME;")
    suspend fun load(): List<FavouriteData>

    @Query(
        """
        SELECT * FROM $TABLE_NAME
        WHERE $ID_FIELD LIKE :itemId;
        """
    )
    suspend fun getById(itemId: Int): FavouriteData

    @Query(
        """
        DELETE FROM $TABLE_NAME
        WHERE $ID_FIELD LIKE :movieId;
        """
    )
    suspend fun deleteById(movieId: Int)

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}