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
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    var loading: Boolean = false
        @Bindable get
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.loading)
        }

    /** Section: Simple Properties. */

    val exceptionLiveData: MutableLiveData<String> = MutableLiveData()

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        onError(exception)
    }

    open fun onError(throwable: Throwable) {
        loading = false
        Timber.e(throwable)
        exceptionLiveData.postValue(throwable.message)
    }
}