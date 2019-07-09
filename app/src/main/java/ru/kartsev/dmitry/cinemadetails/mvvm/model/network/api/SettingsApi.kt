package ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.TMDB_SETTINGS
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration.TmdbConfigurationEntity

interface SettingsApi {
    @GET(TMDB_SETTINGS)
    fun getConfigurationAsync() : Deferred<Response<TmdbConfigurationEntity>>
}