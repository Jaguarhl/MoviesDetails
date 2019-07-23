package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class MovieGenreData(
    @PrimaryKey
    @ColumnInfo(name = GENER_ID_FIELD)
    @Json(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = GENER_NAME_FIELD)
    @Json(name = "name")
    var name: String? = null
) {
    companion object {
        const val TABLE_NAME = "movie_genres"
        const val GENER_ID_FIELD = "id"
        const val GENER_NAME_FIELD = "name"
    }
}