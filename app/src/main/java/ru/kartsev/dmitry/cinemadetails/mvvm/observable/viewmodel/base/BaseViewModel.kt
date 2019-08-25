package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import timber.log.Timber

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

    var exception: String? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.exception)
        }

    /** Section: Simple Properties. */

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        onError(exception)
    }

    protected fun onError(throwable: Throwable) {
        loading = false
        Timber.e(throwable)
        exception = throwable.message
    }
}