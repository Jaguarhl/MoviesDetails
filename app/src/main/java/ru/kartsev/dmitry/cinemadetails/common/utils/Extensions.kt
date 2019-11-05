package ru.kartsev.dmitry.cinemadetails.common.utils

fun String.generateImageLink(imagesBaseUrl: String?): String =
    "$imagesBaseUrl$this"