package ru.kartsev.dmitry.cinemadetails.common.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Util {
    fun formatTime(time: Date, pattern: String): String = SimpleDateFormat(
        pattern, Locale.getDefault()
    ).run {
        timeZone = TimeZone.getDefault()
        format(time)
    }

    fun formatTime(time: Long, pattern: String): String = SimpleDateFormat(
        pattern, Locale.getDefault()
    ).run {
        timeZone = TimeZone.getDefault()
        format(Date(time))
    }

    fun formatTime(time: String, innerPattern: String, pattern: String): String {
        if (time.isEmpty()) return ""
        val inDateFormat = SimpleDateFormat(innerPattern, Locale.getDefault())
        var result = ""
        try {
            val timeFromString = inDateFormat.parse(time)

            result = formatTime(timeFromString, pattern)

        } catch (exception: Exception) {
            // FIXME: Replace by Timber logger
            exception.printStackTrace()
        }

        return result
    }
}