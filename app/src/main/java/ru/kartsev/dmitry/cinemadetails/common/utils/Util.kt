package ru.kartsev.dmitry.cinemadetails.common.utils

import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.abs

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
            Timber.w(exception)
        }

        return result
    }

    fun isExpired(time: Long, periodInHours: Int): Boolean {
        val difference = abs(System.currentTimeMillis() - time)

        return periodInHours < TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS)
    }

    fun getLocale(): String = Locale.getDefault().language
}