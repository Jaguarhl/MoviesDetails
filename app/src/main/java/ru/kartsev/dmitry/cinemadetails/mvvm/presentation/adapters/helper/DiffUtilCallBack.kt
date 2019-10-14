package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper

import androidx.recyclerview.widget.DiffUtil
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable

class DiffUtilCallBack : DiffUtil.ItemCallback<MovieObservable>() {
    override fun areItemsTheSame(oldItem: MovieObservable, newItem: MovieObservable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieObservable, newItem: MovieObservable): Boolean {
        return oldItem.title == newItem.title
            && oldItem.adult == newItem.adult
            && oldItem.overview == newItem.overview
    }

}