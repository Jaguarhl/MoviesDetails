package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.ConfigurationDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.ConfigurationData
import timber.log.Timber

class ConfigurationStorage : KoinComponent {
    /** Section: Injections. */

    private val configurationDao: ConfigurationDao by inject()

    /** Section: Public Methods. */

    suspend fun saveConfiguration(data: ConfigurationData) = try {
        Timber.d("Insert poster configuration list: START.")
        configurationDao.insert(data)
        Timber.d("Insert poster configuration list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Insert poster configuration list: FAILED.")
    }

    suspend fun loadConfiguration(): ConfigurationData? = try {
        Timber.d("Get poster configuration list: START.")
        val result = configurationDao.configuration()
        Timber.d("Insert poster configuration list: FINISH.")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Clear poster configuration list: FAILED.")
        null
    }

    suspend fun clearConfiguration() = try {
        Timber.d("Clear poster configuration list: START.")
        configurationDao.clear()
        Timber.d("Clear poster configuration list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Clear poster configuration list: FAILED.")
    }
}