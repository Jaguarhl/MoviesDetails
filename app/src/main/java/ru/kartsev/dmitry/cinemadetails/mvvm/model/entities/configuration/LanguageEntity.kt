package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class LanguageEntity(
    @Json(name = "english_name")
    val english_name: String,
    @Json(name = "iso_639_1")
    val iso_639_1: String,
    @Json(name = "name")
    val name: String
)