package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base

import androidx.databinding.Bindable
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel

abstract class MovieListBaseViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    var loading: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    /** Section: Simple Properties. */

    var moviePosterSize: String? = null

    /** Section: Abstract Methods. */

    abstract fun movieItemClicked(id: Int)

}