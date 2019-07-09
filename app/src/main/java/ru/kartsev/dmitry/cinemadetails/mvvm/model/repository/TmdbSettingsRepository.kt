package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class TmdbSettingsRepository : BaseRepository() {
    /** Section: Injections. */

    private val settingsApi: SettingsApi by inject()

    var imagesBaseUrl: String? = null

    suspend fun getTmdbSettings() {
        val settings = safeApiCall(
            call = { settingsApi.getConfigurationAsync().await() },
            errorMessage = "Error Fetching TMDB Settings."
        )

        imagesBaseUrl = settings?.images?.secure_base_url
    }
}