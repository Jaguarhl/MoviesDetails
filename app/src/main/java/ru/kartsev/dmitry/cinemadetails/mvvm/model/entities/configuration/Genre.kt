package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData

data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) {
    companion object {
        fun toGenreDataList(list: List<Genre>): List<GenreData> {
            return list.map { GenreData(id = it.id, name = it.name) }
        }
    }
}