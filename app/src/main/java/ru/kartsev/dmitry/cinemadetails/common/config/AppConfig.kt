package ru.kartsev.dmitry.cinemadetails.common.config

import ru.kartsev.dmitry.cinemadetails.BuildConfig

object AppConfig {
    const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    // FIXME: Made this option
    const val LANGUAGE = "ru"
    const val MAX_CAST_ORDER = 4
}