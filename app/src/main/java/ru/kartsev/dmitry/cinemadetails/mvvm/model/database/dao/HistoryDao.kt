package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.history.HistoryEventData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.history.HistoryEventData.Companion.TABLE_NAME

@Dao
interface HistoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(historyEvent: HistoryEventData): Long

    @Insert(onConflict = REPLACE)
    suspend fun save(list: List<HistoryEventData>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME;")
    suspend fun load(): List<HistoryEventData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}