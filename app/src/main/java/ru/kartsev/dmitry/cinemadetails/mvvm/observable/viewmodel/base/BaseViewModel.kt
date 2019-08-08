package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base

import androidx.databinding.Bindable
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel

abstract class BaseViewModel : ObservableViewModel(), KoinComponent {

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
            field = if (field == value) return else value
            notifyPropertyChanged(BR.loading)
        }
}