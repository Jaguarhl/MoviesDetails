package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MovieVideoData(
    @ColumnInfo(name = MOVIE_ID_FIELD)
    var movie_id: Int,

    @PrimaryKey
    @ColumnInfo(name = MOVIE_VIDEO_ID_FIELD)
    var id: String,

    @ColumnInfo(name =  MOVIE_ISO_3166_1_FIELD)
    var iso_3166_1: String,

    @ColumnInfo(name = MOVIE_ISO_639_1_FIELD)
    var iso_639_1: String,

    @ColumnInfo(name = MOVIE_KEY_FIELD)
    var key: String,

    @ColumnInfo(name = MOVIE_NAME_FIELD)
    var name: String,

    @ColumnInfo(name = MOVIE_SITE_FIELD)
    var site: String,

    @ColumnInfo(name = MOVIE_SIZE_FIELD)
    var size: Int,

    @ColumnInfo(name = MOVIE_TYPE_FIELD)
    var type: String
) {
    companion object {
        const val TABLE_NAME = "movie_videos"

        const val MOVIE_ID_FIELD = "movie_id"
        const val MOVIE_VIDEO_ID_FIELD = "id"
        const val MOVIE_ISO_3166_1_FIELD = "iso_3166_1"
        const val MOVIE_ISO_639_1_FIELD = "iso_639_1"
        const val MOVIE_KEY_FIELD = "key"
        const val MOVIE_NAME_FIELD = "name"
        const val MOVIE_SITE_FIELD = "site"
        const val MOVIE_SIZE_FIELD = "size"
        const val MOVIE_TYPE_FIELD = "type"
    }
}