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
    fun fromGenresList(string: String): List<GenreData> {
        val list = string.split(",").map { it }
        return list.map {
            GenreData(
                it.split("-")[0].toInt(),
                it.split("-")[1],
                it.split("-")[2]

        ) }
    }

    @TypeConverter
    @JvmStatic
    fun fromGenresList(list: List<GenreData>): String = list.joinToString(",") { "${it.id}-${it.language}-${it.name}" }
}