package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper

interface DiffItemObservable {
    fun id(): Any? = hashCode()
}