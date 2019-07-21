package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.ConfigurationData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class TmdbSettingsRepository(private val lifeTime: Int) : BaseRepository() {
    /** Section: Injections. */

    private val util: Util by inject()
    private val settingsApi: SettingsApi by inject()
    private val configurationStorage: ConfigurationStorage by inject()

    var imagesBaseUrl: String? = null
    val languagesList = mutableListOf<String>()
    val backdropSizes = mutableListOf<String>()
    val posterSizes = mutableListOf<String>()
    val profileSizes = mutableListOf<String>()

    suspend fun getTmdbSettings() {
        // TODO: Implement exceptions handler and message to user.
        val data = configurationStorage.loadConfiguration()

        if (data != null && !util.isExpired(data.timeStamp, lifeTime)) {
            imagesBaseUrl = data.baseUrl
            backdropSizes.addAll(data.backdropSizes ?: listOf())
            posterSizes.addAll(data.posterSizes ?: listOf())
            profileSizes.addAll(data.profileSizes ?: listOf())
        } else {
            val settings = safeApiCall(
                call = { settingsApi.getConfigurationAsync().await() },
                errorMessage = "Error Fetching TMDB Settings."
            )

            if (settings?.images == null) return
            configurationStorage.clearConfiguration()

            with(settings) {
                configurationStorage.saveConfiguration(
                    ConfigurationData(
                        System.currentTimeMillis(),
                        images.base_url, images.secure_base_url, change_keys, images.backdrop_sizes, images.logo_sizes,
                        images.poster_sizes, images.profile_sizes, images.still_sizes
                    )
                )

                with(images) {
                    imagesBaseUrl = secure_base_url

                    backdropSizes.addAll(backdrop_sizes)
                    posterSizes.addAll(poster_sizes)
                    profileSizes.addAll(profile_sizes)
                }
            }
        }
    }

    suspend fun getLanguagesList(): List<String> {
        // FIXME: Implement loading and caching languages.
//        if (settings?.images == null) return
//
//        val languages = safeApiCall(
//            call = { settingsApi.getSupportedLanguages().await() },
//            errorMessage = "Error Fetching TMDB Languages."
//        )
//
//
//        languages?.let { list->
//            languagesList.addAll(list.map { it.iso_639_1 })
//        }
        return listOf()
    }
}
