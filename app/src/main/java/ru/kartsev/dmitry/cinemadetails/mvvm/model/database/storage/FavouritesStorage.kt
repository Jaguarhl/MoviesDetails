package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule.FAVOURITES_STORAGE_DAO
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.FavouritesDao
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData
import timber.log.Timber

class FavouritesStorage : KoinComponent {

    /** Section: Injections. */

    private val favouritesDao: FavouritesDao by inject(named(FAVOURITES_STORAGE_DAO))

    /** Section: Public Methods. */

    suspend fun getFavourites(): List<FavouriteData>? = try {
        Timber.d("Get favourites list: START.")
        val result = favouritesDao.load()
        Timber.d("Get favourites list: FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Load favourites list: FAILED.")
        null
    }

    suspend fun addFavourite(favourite: FavouriteData) = try {
        Timber.d("Insert favourite data: START. ($favourite)")
        favouritesDao.insert(favourite)
        Timber.d("Insert favourite data: FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Insert favourite data: FAILED.")
    }

    suspend fun getFavouriteById(itemId: Int): FavouriteData? = try {
        Timber.d("Get favourite by id ($itemId): START.")
        val result = favouritesDao.getById(itemId)
        Timber.d("Get favourite by id ($itemId): FINISH. ($result)")
        result
    } catch (exception: Exception) {
        Timber.w(exception, "Get favourite by id ($itemId): FAILED.")
        null
    }

    suspend fun removeFavouriteById(itemId: Int) = try {
        Timber.d("Remove favourite by id ($itemId): START.")
        favouritesDao.deleteById(itemId)
        Timber.d("Remove favourite by id ($itemId): FINISH.")
    } catch (exception: Exception) {
        Timber.w(exception, "Remove favourite by id ($itemId): FAILED.")
    }
}