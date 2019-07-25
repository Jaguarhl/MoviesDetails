package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_GENRES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.TMDB_LANGUAGES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.TMDB_SETTINGS
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration.LanguageEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration.MovieGenresEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration.TmdbConfigurationEntity

interface SettingsApi {
    @GET(TMDB_SETTINGS)
    fun getConfigurationAsync() : Deferred<Response<TmdbConfigurationEntity>>

    @GET(TMDB_LANGUAGES)
    fun getSupportedLanguagesAsync(): Deferred<Response<List<LanguageEntity>>>

    @GET(MOVIE_GENRES)
    fun getMovieGenresAsync(
        @Query("language") language: String?
    ): Deferred<Response<MovieGenresEntity>>
}