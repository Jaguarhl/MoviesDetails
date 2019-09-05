package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.FavouritesStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.favourites.FavouriteData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class FavouritesRepository(private val favouritesStorage: FavouritesStorage) : BaseRepository() {

    /** Section: Private Methods. */

    suspend fun getFavouritesList(): List<FavouriteData>? = favouritesStorage.getFavourites()

    suspend fun addFavouriteToList(movieId: Int, timeStamp: Long) {
        val favourite = FavouriteData(type = FavouriteData.Type.ITEM_MOVIE, itemId = movieId, date = timeStamp)
        favouritesStorage.addFavourite(favourite)
    }

    suspend fun getFavouriteById(itemId: Int): FavouriteData? = favouritesStorage.getFavouriteById(itemId)

    suspend fun removeFavouriteFromList(itemId: Int) = favouritesStorage.removeFavouriteById(itemId)
}