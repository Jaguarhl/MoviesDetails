package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Images(
    @Json(name = "backdrop_sizes")
    val backdrop_sizes: List<String>,
    @Json(name = "base_url")
    val base_url: String,
    @Json(name = "logo_sizes")
    val logo_sizes: List<String>,
    @Json(name = "poster_sizes")
    val poster_sizes: List<String>,
    @Json(name = "profile_sizes")
    val profile_sizes: List<String>,
    @Json(name = "secure_base_url")
    val secure_base_url: String,
    @Json(name = "still_sizes")
    val still_sizes: List<String>
)