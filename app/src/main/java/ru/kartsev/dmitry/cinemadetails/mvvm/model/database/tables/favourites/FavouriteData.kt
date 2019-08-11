package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.DatabaseConverters
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavouriteData(
    @PrimaryKey
    @ColumnInfo(name = ID_FIELD)
    var itemId: Int,

    @ColumnInfo(name = TYPE_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var type: Type
) {
    companion object {
        const val TABLE_NAME = "user_favourites"

        const val ID_FIELD = "id"
        const val TYPE_FIELD = "type"
    }

    enum class Type(
        val value: String
    ) {
        ITEM_MOVIE("movie"),
    }
}