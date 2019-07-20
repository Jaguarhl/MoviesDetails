package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json

data class TmdbConfigurationEntity(
    @Json(name = "change_keys")
    val change_keys: List<String>,
    @Json(name = "images")
    val images: Images
)