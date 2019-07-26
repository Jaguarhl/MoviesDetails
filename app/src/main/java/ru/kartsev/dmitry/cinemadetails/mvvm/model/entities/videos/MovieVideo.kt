package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData

data class MovieVideo(
    @Json(name = "id")
    val id: String,
    @Json(name = "iso_3166_1")
    val iso_3166_1: String,
    @Json(name = "iso_639_1")
    val iso_639_1: String,
    @Json(name = "key")
    val key: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "site")
    val site: String,
    @Json(name = "size")
    val size: Int,
    @Json(name = "type")
    val type: String
) {
    companion object {
        fun getMovieVideoData(movieId: Int, entity: MovieVideo): MovieVideoData = with(entity) {
            MovieVideoData(
                movieId, id, iso_3166_1, iso_639_1, key, name, site, size, type
            )
        }
    }
}