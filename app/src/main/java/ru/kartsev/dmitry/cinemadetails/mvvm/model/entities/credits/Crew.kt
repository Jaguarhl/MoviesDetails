package ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits

import com.squareup.moshi.Json

data class Crew(
    @Json(name = "credit_id")
    val credit_id: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "job")
    val job: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "profile_path")
    val profile_path: String?
)