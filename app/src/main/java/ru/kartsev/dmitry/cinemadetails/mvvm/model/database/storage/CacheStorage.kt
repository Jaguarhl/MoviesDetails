package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.CacheDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData
import timber.log.Timber

class CacheStorage(private val cacheDao: CacheDao) : KoinComponent {
    suspend fun getCachedResponse(urlKey: String): CachedData? = try {
        Timber.d("Get cached response for $urlKey: START.")
        val result = cacheDao.getCached(urlKey)
        Timber.d("Get cached response for $urlKey: FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Get cached response for $urlKey: FAILED.")
        null
    }

    suspend fun addToCache(data: CachedData) = try {
        Timber.d("Save data ($data) to cache: START.")
        cacheDao.insert(data)
        Timber.d("Save data ($data) to cache: FINISH.")
    } catch (exception: java.lang.Exception) {
        Timber.w(exception, "Save to cache $data: FAILED.")
    }

    suspend fun deleteCached(urlKey: String) = try {
        Timber.d("Delete cache for $urlKey: START.")
        cacheDao.deleteCacheFor(urlKey)
        Timber.d("Delete cache for $urlKey: FINISH.")
    } catch (exception: java.lang.Exception) {
        Timber.w(exception, "Delete cache for $urlKey: FAILED.")
    }

    suspend fun clearCache() = try {
        Timber.d("Clear cache: START.")
        cacheDao.clear()
        Timber.d("Clear cache: FINISH.")
    } catch (exception: java.lang.Exception) {
        Timber.w(exception, "Clear cache: FAILED.")
    }
}