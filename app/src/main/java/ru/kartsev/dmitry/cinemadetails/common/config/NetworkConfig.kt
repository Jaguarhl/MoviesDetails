package ru.kartsev.dmitry.cinemadetails.common.config

object NetworkConfig {
    const val CACHE_SIZE = 30L * 1024 * 1024
    const val CONNECTION_TIMEOUT: Long = 30

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR_MOVIE = "movie/popular"
    const val NOW_PLAYING_MOVIE = "movie/now_playing"
    const val MOVIE_DETAILS = "movie/{id}"
    const val MOVIE_ALTERNATIVE_TITLES = "movie/{movie_id}/alternative_titles"
    const val MOVIE_TRANSLATIONS = "movie/{movie_id}/translations"
    const val MOVIE_VIDEOS = "movie/{movie_id}/videos"
    const val MOVIE_KEYWORDS = "movie/{movie_id}/keywords"
    const val MOVIE_CREDITS = "movie/{movie_id}/credits"
    const val MOVIE_SIMILAR = "movie/{movie_id}/similar"
    const val MOVIE_RELEASE_DATES = "movie/{movie_id}/release_dates"
    const val MOVIE_GENRES = "genre/movie/list"
    const val MOVIE_CHANGES = "movie/changes"
    const val MOVIE_IMAGES = "movie/{movie_id}/images"
    const val TMDB_SETTINGS = "configuration"
    const val TMDB_LANGUAGES = "configuration/languages"

    const val PAGE_SIZE = 20
}