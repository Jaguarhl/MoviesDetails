package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData.Companion.TABLE_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData.Companion.URL_FIELD

@Dao
interface CacheDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(data: CachedData): Long

    @Query(
        """
        SELECT * FROM $TABLE_NAME
        WHERE $URL_FIELD LIKE :url;
        """
    )
    suspend fun getCached(url: String): CachedData

    @Query(
        """
            DELETE FROM $TABLE_NAME
            WHERE $URL_FIELD LIKE :url;
        """
    )
    suspend fun deleteCacheFor(url: String)

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}