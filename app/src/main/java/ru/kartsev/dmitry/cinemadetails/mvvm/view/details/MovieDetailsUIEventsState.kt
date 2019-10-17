package ru.kartsev.dmitry.cinemadetails.mvvm.view.details

sealed class MovieDetailsUIEventsState

object CollapseToolbarEvent: MovieDetailsUIEventsState()
object MarkFavouriteEvent: MovieDetailsUIEventsState()
data class OpenImageEvent(val imagePath: String, val imageDimensions: String): MovieDetailsUIEventsState()
