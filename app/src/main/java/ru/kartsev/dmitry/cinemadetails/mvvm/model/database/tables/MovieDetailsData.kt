package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieDetailsData.Companion.TABLE_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.Language
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieGenre
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.ProductionCompany
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.ProductionCountry

@Entity(tableName = TABLE_NAME)
data class MovieDetailsData(
    @ColumnInfo(name = ADULT_FIELD)
    @Json(name = "adult")
    var adult: Boolean? = null,

    @ColumnInfo(name = BACKDROP_PATH_FIELD)
    @Json(name = "backdrop_path")
    var backdrop_path: String? = null,
    @Json(name = "belongs_to_collection")
    var belongs_to_collection: Any? = null,
    @Json(name = "budget")
    var budget: Long? = null,
    @Json(name = "genres")
    var genres: List<MovieGenre>? = null,
    @Json(name = "homepage")
    var homepage: String? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "imdb_id")
    var imdb_id: String? = null,
    @Json(name = "original_language")
    var original_language: String? = null,
    @Json(name = "original_title")
    var original_title: String? = null,
    @Json(name = "overview")
    var overview: String? = null,
    @Json(name = "popularity")
    var popularity: Double? = null,
    @Json(name = "poster_path")
    var poster_path: String? = null,
    @Json(name = "production_companies")
    var production_companies: List<ProductionCompany>? = null,
    @Json(name = "production_countries")
    var production_countries: List<ProductionCountry>? = null,
    @Json(name = "release_date")
    var release_date: String? = null,
    @Json(name = "revenue")
    var revenue: Long? = null,
    @Json(name = "runtime")
    var runtime: Int? = null,
    @Json(name = "spoken_languages")
    var spoken_languages: List<Language>? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "tagline")
    var tagline: String? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "video")
    var video: Boolean? = null,
    @Json(name = "vote_average")
    var vote_average: Double? = null,
    @Json(name = "vote_count")
    var vote_count: Int? = null
) {
    companion object {
        const val TABLE_NAME = "movie_details"

        const val ADULT_FIELD = "adult"
        const val BACKDROP_PATH_FIELD = "backdrop_path"
    }
}