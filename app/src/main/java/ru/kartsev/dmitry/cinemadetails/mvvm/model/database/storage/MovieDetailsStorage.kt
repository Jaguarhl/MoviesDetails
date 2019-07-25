package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.GenresDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieDetailsDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieDetailsData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import timber.log.Timber

class MovieDetailsStorage : KoinComponent {
    /** Section: Injections. */

    private val genresDao: GenresDao by inject()
    private val movieDetailsDao: MovieDetailsDao by inject()

    /** Section: Public Methods. */

    suspend fun saveGenresList(list: List<GenreData>) = try {
        Timber.d("Save genres list: START. ($list)")
        genresDao.save(list)
        Timber.d("Save genres list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Save genres list: FAILED.")
    }

    suspend fun loadGenresList(): List<GenreData>? = try {
        Timber.d("Get genres list: START.")
        val result = genresDao.load()
        Timber.d("Get genres list: FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Load genres list: FAILED.")
        null
    }

    suspend fun clearGenresList() = try {
        Timber.d("Clear genres list: START.")
        genresDao.clear()
        Timber.d("Clear genres list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Clear genres list: FAILED.")
    }

    suspend fun loadMovieDetailsById(movieId: Int): MovieDetailsData? = try {
        Timber.d("Load movie details by id($movieId): START.")
        val result = movieDetailsDao.loadById(movieId)
        Timber.d("Load movie details by id($movieId): FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Load movie details by id($movieId): FAILED.")
        null
    }

    suspend fun saveMovieDetails(data: MovieDetailsData) = try {
        Timber.d("Save movie details ($data): START.")
        movieDetailsDao.save(data)
        Timber.d("Save movie details ($data): FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Save movie details ($data): FAILED.")
    }
}