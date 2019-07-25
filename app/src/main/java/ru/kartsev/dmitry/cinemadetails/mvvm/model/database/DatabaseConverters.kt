package ru.kartsev.dmitry.cinemadetails.mvvm.model.database

import androidx.room.TypeConverter
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData

object DatabaseConverters : KoinComponent {
    @TypeConverter
    @JvmStatic
    fun fromChangeKeys(strings: List<String>): String = strings.joinToString(",") { it }

    @TypeConverter
    @JvmStatic
    fun toChangeKeys(string: String): List<String> = string.split(",").map { it.trim() }

    @TypeConverter
    @JvmStatic
    fun fromIntList(list: List<Int>): String = list.joinToString(",") { it.toString() }

    @TypeConverter
    @JvmStatic
    fun toIntList(string: String): List<Int> = string.split(",").map { it.toInt() }
}