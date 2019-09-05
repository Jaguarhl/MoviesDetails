package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.history.HistoryEventData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class HistoryEventData(
    @PrimaryKey
    @ColumnInfo(name = MOVIE_ID_FIELD)
    var movieId: Int,

    @ColumnInfo(name = EVENT_TIME)
    var dateTime: Long
) {
    companion object {
        const val TABLE_NAME = "history"

        const val MOVIE_ID_FIELD = "movie_id"
        const val EVENT_TIME = "timestamp"
    }
}