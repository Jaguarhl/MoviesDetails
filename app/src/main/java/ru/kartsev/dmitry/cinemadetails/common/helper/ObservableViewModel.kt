package ru.kartsev.dmitry.cinemadetails.common.helper

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * Generated code, which includes ViewModel and Observable functionality.
 */
open class ObservableViewModel : ViewModel(), Observable {
    private var callbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                callbacks = PropertyChangeRegistry()
            }
        }
        callbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChanged() {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.notifyCallbacks(this, fieldId, null)
    }
}