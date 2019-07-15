package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits

import com.squareup.moshi.Json

data class Cast(
    @Json(name = "cast_id")
    val cast_id: Int,
    @Json(name = "character")
    val character: String,
    @Json(name = "credit_id")
    val credit_id: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "profile_path")
    val profile_path: String?
)