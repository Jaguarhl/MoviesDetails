package ru.kartsev.dmitry.cinemadetails.mvvm.model.converter

import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class MovieItemConverter : KoinComponent {
    fun convertToObservable(response: List<MovieEntity>?): List<MovieObservable>? {
        return response?.map {
            MovieObservable(
                it.id,
                it.vote_average.toString(),
                it.title,
                it.overview,
                it.poster_path ?: "",
                it.backdrop_path ?: "",
                it.adult,
                it.release_date
            )
        }
    }
}