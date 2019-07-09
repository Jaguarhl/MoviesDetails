package ru.kartsev.dmitry.cinemadetails.common.config

object NetworkConfig {
    const val CACHE_SIZE = 30L * 1024 * 1024

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR_MOVIE = "movie/popular"
    const val MOVIE_DETAILS = "movie/{id}"
    const val MOVIE_ALTERNATIVE_TITLES = "movie/{movie_id}/alternative_titles"
    const val MOVIE_TRANSLATIONS = "movie/{movie_id}/translations"
    const val TMDB_SETTINGS = "configuration"

    const val PAGE_SIZE = 20
}