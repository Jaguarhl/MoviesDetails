package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.TMDB_SETTINGS_REPOSITORY_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.BaseViewModel

class MovieImageViewModel : BaseViewModel() {
    /** Section: Injections. */

    private val settingsRepository: TmdbSettingsRepository by inject(named(TMDB_SETTINGS_REPOSITORY_NAME))

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