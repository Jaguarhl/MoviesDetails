package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_ALTERNATIVE_TITLES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_DETAILS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_TRANSLATIONS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.POPULAR_MOVIE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieAlternativeTitlesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieTranslationsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.PopularMoviesEntity

interface MoviesApi {
    @GET(POPULAR_MOVIE)
    fun getPopularMovieAsync(
        @Query("language") language: String?,
        @Query("page") page: Int
    ): Deferred<Response<PopularMoviesEntity>>

    @GET(MOVIE_DETAILS)
    fun getMovieByIdAsync(
        @Path("id") id: Int
    ): Deferred<Response<MovieDetailsEntity>>

    @GET(MOVIE_ALTERNATIVE_TITLES)
    fun getMovieAlternativeTitlesAsync(
        @Path("movie_id") movie_id: Int,
        @Query("country") country: String? = null
    ): Deferred<Response<MovieAlternativeTitlesEntity>>

    @GET(MOVIE_TRANSLATIONS)
    fun getMovieTranslationsAsync(
        @Path("movie_id") movie_id: Int
    ): Deferred<Response<MovieTranslationsEntity>>
}