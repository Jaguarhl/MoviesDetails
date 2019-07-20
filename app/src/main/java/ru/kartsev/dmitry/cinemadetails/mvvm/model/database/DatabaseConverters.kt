package ru.kartsev.dmitry.cinemadetails.mvvm.model.database

import androidx.room.TypeConverter
import org.koin.standalone.KoinComponent

object DatabaseConverters : KoinComponent {
    @TypeConverter
    @JvmStatic
    fun fromChangeKeys(strings: List<String>): String = strings.joinToString(",") { it }

    @TypeConverter
    @JvmStatic
    fun toChangeKeys(string: String): List<String> = string.split(",").map { it.trim() }
}