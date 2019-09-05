package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage

import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.dao.HistoryDao

class HistoryStorage(private val historyDao: HistoryDao) : KoinComponent {

}