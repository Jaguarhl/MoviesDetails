package ru.kartsev.dmitry.cinemadetails.mvvm.model.datasource

class NetworkState(val status: Status, val message: String? = null) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}