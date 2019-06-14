package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_DETAILS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.POPULAR_MOVIE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity

interface MoviesApi {
    @GET(POPULAR_MOVIE)
    fun getPopularMovieAsync(
        @Query("page") page:Int
    ): Deferred<Response<TmdbMovieResponseEntity>>

    @GET(MOVIE_DETAILS)
    fun getMovieByIdAsync(@Path("id") id:Int): Deferred<Response<MovieDetailsEntity>>
}