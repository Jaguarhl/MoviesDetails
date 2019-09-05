package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.BaseViewModel

class MovieImageViewModel(private val settingsRepository: TmdbSettingsRepository) : BaseViewModel() {
    /** Section: Bindable Properties. */

    var movieImagePath: String? = null

    /** Section: Simple properties. */

    var movieImageSize: String? = null
    var movieImageToSavePathUri: String? = null

    /** Section: Initialization. */

    fun initializeByDefault(imagePath: String) {
        movieImagePath = imagePath
        movieImageSize = settingsRepository.backdropSizes.last()
    }
}