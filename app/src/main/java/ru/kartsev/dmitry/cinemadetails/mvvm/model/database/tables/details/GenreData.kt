package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.koin.java.KoinJavaComponent.get
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class GenreData(
    @PrimaryKey
    @ColumnInfo(name = GENRE_ID_FIELD)
    @Json(name = "id")
    var id: Int,

    @ColumnInfo(name = GENRE_LANGUAGE_FIELD)
    var language: String = get(Util::class.java).getLocale(),

    @ColumnInfo(name = GENRE_NAME_FIELD)
    @Json(name = "name")
    var name: String
) {
    companion object {
        const val TABLE_NAME = "movie_genres"

        const val GENRE_ID_FIELD = "genre_id"
        const val GENRE_LANGUAGE_FIELD = "genre_language"
        const val GENRE_NAME_FIELD = "genre_name"
    }
}