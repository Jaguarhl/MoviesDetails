package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.ConfigurationDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.ConfigurationData
import timber.log.Timber

class ConfigurationStorage(private val configurationDao: ConfigurationDao) : KoinComponent {

    /** Section: Public Methods. */

    suspend fun saveConfiguration(data: ConfigurationData) = try {
        Timber.d("Insert configuration data: START. ($data)")
        configurationDao.insert(data)
        Timber.d("Insert configuration data: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Insert configuration data: FAILED.")
    }

    suspend fun loadConfiguration(): ConfigurationData? = try {
        Timber.d("Get configuration data: START.")
        val result = configurationDao.configuration()
        Timber.d("Get configuration data: FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Load configuration data: FAILED.")
        null
    }

    suspend fun clearConfiguration() = try {
        Timber.d("Clear configuration data: START.")
        configurationDao.clear()
        Timber.d("Clear configuration data: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Clear configuration data: FAILED.")
    }
}