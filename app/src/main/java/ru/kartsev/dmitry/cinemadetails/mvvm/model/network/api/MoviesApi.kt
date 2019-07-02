package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_DETAILS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.POPULAR_MOVIE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import io.reactivex.Single

interface MoviesApi {
    @GET(POPULAR_MOVIE)
    fun getPopularMovies(
        @Query("page") page: Int
    ): Single<TmdbMovieResponseEntity>

    @GET(MOVIE_DETAILS)
    fun getMovieById(@Path("id") id: Int): Single<MovieDetailsEntity>
}