package ru.kartsev.dmitry.cinemadetails.mvvm.view.helper

import androidx.databinding.Observable
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

abstract class DefaultPropertyHandler<in T : LifecycleOwner>(reference: T) : Observable.OnPropertyChangedCallback() {
    private val reference = WeakReference(reference)

    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        onPropertyChanged(reference.get() ?: return, propertyId)
    }

    fun attach() {
        observableOrNull(reference.get() ?: return)?.addOnPropertyChangedCallback(this)
    }

    fun detach() {
        observableOrNull(reference.get() ?: return)?.removeOnPropertyChangedCallback(this)
    }

    protected abstract fun onPropertyChanged(reference: T, propertyId: Int)

    protected abstract fun observableOrNull(reference: T): Observable?
}