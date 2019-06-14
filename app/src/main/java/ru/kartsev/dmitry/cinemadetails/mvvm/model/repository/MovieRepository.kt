package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class MovieRepository : BaseRepository() {
    /** Section: Injections. */

    private val moviesApi: MoviesApi by inject()

    suspend fun getPopularMovies(page: Int): TmdbMovieResponseEntity? {
        return safeApiCall(
            call = { moviesApi.getPopularMovieAsync(page).await() },
            errorMessage = "Error Fetching Popular Movies."
        )
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieByIdAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Details."
        )
    }
}