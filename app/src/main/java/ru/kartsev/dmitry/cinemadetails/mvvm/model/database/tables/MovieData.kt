package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MovieData(
    @field:Json(name = "adult")
    val adult: Boolean,

    @field:Json(name = "backdrop_path")
    val backdrop_path: String?,

    @field:Json(name = "genre_ids")
    val genre_ids: List<Int>,

    @PrimaryKey
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "original_language")
    val original_language: String,

    @field:Json(name = "original_title")
    val original_title: String,

    @field:Json(name = "overview")
    val overview: String,

    @field:Json(name = "popularity")
    val popularity: Double,

    @field:Json(name = "poster_path")
    val poster_path: String?,

    @field:Json(name = "release_date")
    val release_date: String,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "video")
    val video: Boolean,

    @field:Json(name = "vote_average")
    val vote_average: Double,

    @field:Json(name = "vote_count")
    val vote_count: Int
) {
    companion object {
        const val TABLE_NAME = "movies_list"
    }
}