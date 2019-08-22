package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule.FAVOURITES_STORAGE_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.FavouritesStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class FavouritesRepository : BaseRepository() {

    /** Section: Injections. */
    private val favouritesStorage: FavouritesStorage by inject(named(FAVOURITES_STORAGE_NAME))

    /** Section: Private Methods. */

    suspend fun getFavouritesList(): List<FavouriteData>? = favouritesStorage.getFavourites()

    suspend fun addFavouriteToList(movieId: Int, timeStamp: Long) {
        val favourite = FavouriteData(type = FavouriteData.Type.ITEM_MOVIE, itemId = movieId, date = timeStamp)
        favouritesStorage.addFavourite(favourite)
    }

    suspend fun getFavouriteById(itemId: Int): FavouriteData? = favouritesStorage.getFavouriteById(itemId)

    suspend fun removeFavouriteFromList(itemId: Int) = favouritesStorage.removeFavouriteById(itemId)
}