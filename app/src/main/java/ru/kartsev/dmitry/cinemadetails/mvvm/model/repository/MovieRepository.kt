package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.TmdbMovieResponseEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository
import io.reactivex.Single

class MovieRepository : BaseRepository() {
    /** Section: Injections. */

    private val moviesApi: MoviesApi by inject()

    fun getPopularMovies(page: Int): Single<TmdbMovieResponseEntity> {
        return moviesApi.getPopularMovies(page)
    }

    fun getMovieDetails(movieId: Int): Single<MovieDetailsEntity> {
        return moviesApi.getMovieById(movieId)
    }
}