package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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

    @ColumnInfo(name = BELONGS_TO_COLLECTION_FIELD)
    @Json(name = "belongs_to_collection")
    var belongs_to_collection: Any? = null,

    @ColumnInfo(name = BUDGET_FIELD)
    @Json(name = "budget")
    var budget: Long? = null,

    @ColumnInfo(name = GENRES_FIELD)
    @Json(name = "genres")
    var genres: List<MovieGenre>? = null,

    @ColumnInfo(name = HOMEPAGE_FIELD)
    @Json(name = "homepage")
    var homepage: String? = null,

    @PrimaryKey
    @ColumnInfo(name = ID_FIELD)
    @Json(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = TMDB_ID_FIELD)
    @Json(name = "imdb_id")
    var imdb_id: String? = null,

    @ColumnInfo(name = ORIGINAL_LANGUAGE_FIELD)
    @Json(name = "original_language")
    var original_language: String? = null,

    @ColumnInfo(name = ORIGINAL_TITLE_FIELD)
    @Json(name = "original_title")
    var original_title: String? = null,

    @ColumnInfo(name = OVERVIEW_FIELD)
    @Json(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = POPULARITY_FIELD)
    @Json(name = "popularity")
    var popularity: Double? = null,

    @ColumnInfo(name = POSTER_PATH_FIELD)
    @Json(name = "poster_path")
    var poster_path: String? = null,

    @ColumnInfo(name = PRODUCTION_COMPANIES_FIELD)
    @Json(name = "production_companies")
    var production_companies: List<ProductionCompany>? = null,

    @ColumnInfo(name = PRODUCTION_COUNTRIES_FIELD)
    @Json(name = "production_countries")
    var production_countries: List<ProductionCountry>? = null,

    @ColumnInfo(name = RELEASE_DATE_FIELD)
    @Json(name = "release_date")
    var release_date: String? = null,

    @ColumnInfo(name = REVENUE_FIELD)
    @Json(name = "revenue")
    var revenue: Long? = null,

    @ColumnInfo(name = RUNTIME_FIELD)
    @Json(name = "runtime")
    var runtime: Int? = null,

    @ColumnInfo(name = SPOKEN_LANGUAGES_FIELD)
    @Json(name = "spoken_languages")
    var spoken_languages: List<Language>? = null,

    @ColumnInfo(name = STATUS_FIELD)
    @Json(name = "status")
    var status: String? = null,

    @ColumnInfo(name = TAGLINE_FIELD)
    @Json(name = "tagline")
    var tagline: String? = null,

    @ColumnInfo(name = TITLE_FIELD)
    @Json(name = "title")
    var title: String? = null,

    @ColumnInfo(name = VIDEO_FIELD)
    @Json(name = "video")
    var video: Boolean? = null,

    @ColumnInfo(name = VOTE_AVERAGE_FIELD)
    @Json(name = "vote_average")
    var vote_average: Double? = null,

    @ColumnInfo(name = VOTE_COUNT_FIELD)
    @Json(name = "vote_count")
    var vote_count: Int? = null
) {
    companion object {
        const val TABLE_NAME = "movie_details"

        const val ADULT_FIELD = "adult"
        const val BACKDROP_PATH_FIELD = "backdrop_path"
        const val BELONGS_TO_COLLECTION_FIELD = "belongs_to_collection"
        const val BUDGET_FIELD = "budget"
        const val GENRES_FIELD = "genres"
        const val HOMEPAGE_FIELD = "homepage"
        const val ID_FIELD = "id"
        const val TMDB_ID_FIELD = "imdb_id"
        const val ORIGINAL_LANGUAGE_FIELD = "original_language"
        const val ORIGINAL_TITLE_FIELD = "original_title"
        const val OVERVIEW_FIELD = "overview"
        const val POPULARITY_FIELD = "popularity"
        const val POSTER_PATH_FIELD = "poster_path"
        const val PRODUCTION_COMPANIES_FIELD = "production_companies"
        const val PRODUCTION_COUNTRIES_FIELD = "production_countries"
        const val RELEASE_DATE_FIELD = "release_date"
        const val REVENUE_FIELD = "revenue"
        const val RUNTIME_FIELD = "runtime"
        const val SPOKEN_LANGUAGES_FIELD = "spoken_languages"
        const val STATUS_FIELD = "status"
        const val TAGLINE_FIELD = "tagline"
        const val TITLE_FIELD = "title"
        const val VIDEO_FIELD = "video"
        const val VOTE_AVERAGE_FIELD = "vote_average"
        const val VOTE_COUNT_FIELD = "vote_count"
    }
}