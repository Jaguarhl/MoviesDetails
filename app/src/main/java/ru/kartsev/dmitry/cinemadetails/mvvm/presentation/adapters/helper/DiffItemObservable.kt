package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper

interface DiffItemObservable {
    fun id(): Any? = hashCode()
}