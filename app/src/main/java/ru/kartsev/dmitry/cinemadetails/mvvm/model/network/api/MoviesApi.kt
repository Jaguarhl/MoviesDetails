package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_ALTERNATIVE_TITLES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_CREDITS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_DETAILS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_IMAGES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_KEYWORDS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_RELEASE_DATES
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_SIMILAR
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_TRANSLATIONS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.MOVIE_VIDEOS
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.NOW_PLAYING_MOVIE
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.POPULAR_MOVIE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits.MovieCreditsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates.ReleaseDatesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieAlternativeTitlesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieTranslationsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.images.MovieImagesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords.MovieKeywordsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.now_playing.NowPlayingMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.PopularMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.similar.SimilarMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos.MovieVideosEntity

interface MoviesApi {
    @GET(POPULAR_MOVIE)
    fun getPopularMovieAsync(
        @Query("language") language: String?,
        @Query("page") page: Int
    ): Deferred<Response<PopularMoviesEntity>>

    @GET(MOVIE_DETAILS)
    fun getMovieByIdAsync(
        @Path("id") id: Int,
        @Query("language") language: String?
    ): Deferred<Response<MovieDetailsEntity>>

    @GET(MOVIE_RELEASE_DATES)
    fun getMovieReleaseDatesAsync(
        @Path("movie_id") movie_id: Int
    ): Deferred<Response<ReleaseDatesEntity>>

    @GET(MOVIE_ALTERNATIVE_TITLES)
    fun getMovieAlternativeTitlesAsync(
        @Path("movie_id") movie_id: Int,
        @Query("country") country: String? = null
    ): Deferred<Response<MovieAlternativeTitlesEntity>>

    @GET(MOVIE_TRANSLATIONS)
    fun getMovieTranslationsAsync(
        @Path("movie_id") movie_id: Int
    ): Deferred<Response<MovieTranslationsEntity>>

    @GET(MOVIE_VIDEOS)
    fun getMovieVideosAsync(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String?
    ): Deferred<Response<MovieVideosEntity>>

    @GET(MOVIE_KEYWORDS)
    fun getMovieKeywordsAsync(
        @Path("movie_id") movie_id: Int
    ): Deferred<Response<MovieKeywordsEntity>>

    @GET(NOW_PLAYING_MOVIE)
    fun getNowPlayingMovieAsync(
        @Query("language") language: String?,
        @Query("page") page: Int,
        @Query("region") region: String?
    ): Deferred<Response<NowPlayingMoviesEntity>>

    @GET(MOVIE_CREDITS)
    fun getMovieCreditsAsync(
        @Path("movie_id") movie_id: Int
    ): Deferred<Response<MovieCreditsEntity>>

    @GET(MOVIE_SIMILAR)
    fun getSimilarMoviesAsync(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<SimilarMoviesEntity>>

    @GET(MOVIE_IMAGES)
    fun getMovieImagesAsync(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String?,
        @Query("include_image_language") includeImageLanguage: String
    ): Deferred<Response<MovieImagesEntity>>
}