package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.ConfigurationStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.LanguageStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.ConfigurationData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.GenreData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository
import timber.log.Timber

class TmdbSettingsRepository(private val lifeTime: Int) : BaseRepository() {
    /** Section: Injections. */

    private val util: Util by inject()
    private val settingsApi: SettingsApi by inject()
    private val configurationStorage: ConfigurationStorage by inject()
    private val languageStorage: LanguageStorage by inject()
    private val movieDetailsStorage: MovieDetailsStorage by inject()

    var imagesBaseUrl: String? = null
    var currentLanguage: String? = null
    val languagesList = mutableListOf<String>()
    val backdropSizes = mutableListOf<String>()
    val posterSizes = mutableListOf<String>()
    val profileSizes = mutableListOf<String>()
    val genresList = mutableListOf<GenreData>()

    suspend fun getTmdbSettings() {
        // TODO: Implement exceptions handler and message to user.
        val data = configurationStorage.loadConfiguration()

        if (data != null && util.isExpired(data.timeStamp, lifeTime).not()) {
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

        languagesList.addAll(getLanguagesList() ?: listOf())
        currentLanguage = if (languagesList.contains(util.getLocale())) { util.getLocale() } else languagesList[0]
        loadGenresList()
        Timber.d("$languagesList,\ncurrent language: $currentLanguage, \ngenres list: $genresList")
    }

    suspend fun loadGenresList() {
        val data = movieDetailsStorage.loadLanguagesList()

        if (data.isNullOrEmpty() || data[0].language.equals(util.getLocale(), true).not()) {
            val genres = safeApiCall(
                call = { settingsApi.getMovieGenresAsync(currentLanguage).await() },
                errorMessage = "Error Fetching Movie Genres For $currentLanguage Locale"
            )

            genres?.genres?.let {
                movieDetailsStorage.saveGenresList(it)
                genresList.addAll(it)
            }
        } else genresList.addAll(data)
    }

    suspend fun getLanguagesList(): List<String>? {
        val data = languageStorage.loadLanguagesList()

        return if (data.isNullOrEmpty()) {
            val languages = safeApiCall(
                call = { settingsApi.getSupportedLanguagesAsync().await() },
                errorMessage = "Error Fetching TMDB Languages."
            )

            languages?.let { list ->
                languageStorage.saveLanguagesList(list.map {
                    LanguageData(
                        englishName = it.english_name,
                        isoCode = it.iso_639_1,
                        name = it.name
                    )
                })

                list.map { it.iso_639_1 }
            }
        } else {
            data.map { it.isoCode!! }
        }
    }
}
