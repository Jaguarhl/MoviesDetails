package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details

import com.squareup.moshi.Json
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData

data class MovieDetailsEntity(
    @Json(name = "adult")
    var adult: Boolean? = null,
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
        fun getDetailsData(
            entity: MovieDetailsEntity,
            language: String?
        ): MovieDetailsData = with(entity) {
            MovieDetailsData(
                false, language, adult, backdrop_path, budget, genres?.map { it.id!! } ?: listOf(),
                homepage, id, imdb_id, original_language, original_title,
                overview, popularity, poster_path, production_companies!!.map { it.id!! },
                production_countries!!.map { it.name!! }, release_date, revenue, runtime,
                spoken_languages?.map { it.iso_639_1!! } ?: listOf(), status, tagline, title,
                video, vote_average, vote_count
            )
        }

        fun getDetailsEntityFromData(
            data: MovieDetailsData,
            genresList: MutableList<GenreData>,
            languagesList: MutableList<LanguageData>
        ): MovieDetailsEntity? = with(data) {
            MovieDetailsEntity(
                adult, backdrop_path, null, budget,
                genresList.filter { genres!!.contains(it.id) }.map { MovieGenre(it.id, it.name) },
                homepage, id, imdb_id, original_language, original_title, overview, popularity,
                poster_path, listOf(),
                production_countries?.map { ProductionCountry("", it) } ?: listOf(),
                release_date, revenue, runtime,
                languagesList.filter { spoken_languages!!.contains(it.isoCode) }.map { Language(it.isoCode, it.name) },
                status, tagline, title, video, vote_average, vote_count
            )
        }
    }
}