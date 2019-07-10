package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class TmdbSettingsRepository : BaseRepository() {
    /** Section: Injections. */

    private val settingsApi: SettingsApi by inject()

    var imagesBaseUrl: String? = null
    var languagesList = listOf<String>()

    suspend fun getTmdbSettings() {
        val settings = safeApiCall(
            call = { settingsApi.getConfigurationAsync().await() },
            errorMessage = "Error Fetching TMDB Settings."
        )

        val languages = safeApiCall(
            call = { settingsApi.getSupportedLanguages().await() },
            errorMessage = "Error Fetching TMDB Languages."
        )

        imagesBaseUrl = settings?.images?.secure_base_url
        languages?.let { list->
            languagesList.toMutableList().apply {
                clear()
                addAll(list.map { it.iso_639_1 })
            }
        }
    }
}