package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.LanguagesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.LanguageData
import timber.log.Timber

class LanguageStorage : KoinComponent {
    /** Section: Injections. */

    private val languagesDao: LanguagesDao by inject()

    /** Section: Public Methods. */

    suspend fun saveLanguagesList(list: List<LanguageData>) = try {
        Timber.d("Save languages list: START. ($list)")
        languagesDao.save(list)
        Timber.d("Save languages list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Save languages list: FAILED.")
    }

    suspend fun loadLanguagesList(): List<LanguageData>? = try {
        Timber.d("Get languages list: START.")
        val result = languagesDao.load()
        Timber.d("Get languages list: FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Load languages list: FAILED.")
        null
    }

    suspend fun clearLanguagesList() = try {
        Timber.d("Clear languages list: START.")
        languagesDao.clear()
        Timber.d("Clear languages list: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Clear languages list: FAILED.")
    }
}