package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieAlternativeTitlesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieTranslationsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.PopularMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos.MovieVideosEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class MovieRepository : BaseRepository() {
    /** Section: Injections. */

    private val moviesApi: MoviesApi by inject()

    suspend fun getPopularMovies(page: Int, language: String? = null): PopularMoviesEntity? {
        return safeApiCall(
            call = { moviesApi.getPopularMovieAsync(language, page).await() },
            errorMessage = "Error Fetching Popular Movies."
        )
    }

    suspend fun getMovieDetails(movieId: Int, language: String? = null): MovieDetailsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieByIdAsync(movieId, language).await() },
            errorMessage = "Error Fetching Movie Details."
        )
    }

    suspend fun getMovieAlternativeTitles(movieId: Int, country: String? = null): MovieAlternativeTitlesEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieAlternativeTitlesAsync(movieId, country).await() },
            errorMessage = "Error Fetching Movie Alternative Titles."
        )
    }

    suspend fun getMovieTranslations(movieId: Int): MovieTranslationsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieTranslationsAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Translations."
        )
    }

    suspend fun getMovieVideos(movieId: Int, language: String? = null): MovieVideosEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieVideosAsync(movieId, language).await() },
            errorMessage = "Error Fetching Movie Videos."
        )
    }
}