package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import androidx.paging.DataSource
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.MovieListDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.MovieData
import timber.log.Timber

class MovieListStorage (private val movieListDao: MovieListDao) : KoinComponent {
    suspend fun getMovieList(): DataSource.Factory<Int, MovieData> = movieListDao.load()

    suspend fun saveList(data: List<MovieData>) = try {
        Timber.d("Save data ($data) to cache: START.")
        movieListDao.save(data)
        Timber.d("Save data ($data) to cache: FINISH.")
    } catch (exception: java.lang.Exception) {
        Timber.w(exception, "Save to cache $data: FAILED.")
    }

    suspend fun clearList() = try {
        Timber.d("Clear movie list: START.")
        movieListDao.clear()
        Timber.d("Clear movie list: FINISH.")
    } catch (exception: java.lang.Exception) {
        Timber.w(exception, "Clear movie list: FAILED.")
    }
}