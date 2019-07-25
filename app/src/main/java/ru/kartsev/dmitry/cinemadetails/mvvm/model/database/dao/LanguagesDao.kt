package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData.Companion.TABLE_NAME

@Dao
interface LanguagesDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(language: LanguageData): Long

    @Insert(onConflict = REPLACE)
    suspend fun save(list: List<LanguageData>): List<Long>

    @Query("SELECT * FROM $TABLE_NAME;")
    suspend fun load(): List<LanguageData>

    @Query("DELETE FROM $TABLE_NAME;")
    suspend fun clear()
}