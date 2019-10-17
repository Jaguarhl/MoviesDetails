package ru.kartsev.dmitry.cinemadetails.mvvm.view.pager

sealed class MovieUIEventState

data class ShowMovieDetails(val movieId: Int): MovieUIEventState()