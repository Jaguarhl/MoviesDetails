package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieGenre

@Parcelize
data class MovieDetailsObservable(
    val adult: Boolean = false,
    val backdropPath: String? = null,
    val collection: Any? = null,
    val budget: Int = 0,
    val genres: List<MovieGenre>? = null,
    val originalLanguage: String? = null,
    val originalTitle: String,
    val id: Int = 0,
    val imdbId: String? = null,
    val overview: String,
    val popularity: Double,
    val posterPath: String? = null,
    val status: String,
    val tagline: String? = null,
    val title: String
) : Parcelable {
    companion object {
        fun fromMovieDetailsEntity(it: MovieDetailsEntity): MovieDetailsObservable {
            return MovieDetailsObservable(
                it.adult ?: false,
                it.backdropPath,
                it.belongsToCollection,
                it.budget ?: 0,
                it.genres,
                it.originalLanguage,
                it.originalTitle ?: "",
                it.id ?: 0,
                it.imdbId,
                it.overview ?: "",
                it.popularity ?: 0.0,
                it.posterPath,
                it.status ?: "",
                it.tagline,
                it.title ?: ""
            )
        }
    }
}