package ru.kartsev.dmitry.cinemadetails.mvvm.model.database

import androidx.room.TypeConverter
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData

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

    @TypeConverter
    @JvmStatic
    fun favouritesTypeToString(type: FavouriteData.Type): String? = when (type) {
        FavouriteData.Type.ITEM_MOVIE -> FavouriteData.Type.ITEM_MOVIE.value
    }

    @TypeConverter
    @JvmStatic
    fun stringToFavouritesType(type: String?): FavouriteData.Type? = when (type) {
        FavouriteData.Type.ITEM_MOVIE.value -> FavouriteData.Type.ITEM_MOVIE
        else -> null
    }
}