package ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable

data class MovieObservable(
    val id: Int,
    val vote_average: String = "",
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdoorPath: String = "",
    val adult: Boolean = false,
    val releaseDate: String = ""
) {
    val isPosterVisible = posterPath.isNotEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MovieObservable) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}