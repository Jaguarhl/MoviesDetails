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