package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CachedData (
    @PrimaryKey
    @ColumnInfo(name = URL_FIELD)
    var urlKey: String,
    @ColumnInfo(name = TIMESTAMP_FIELD)
    var timeStamp: Long? = null,
    @ColumnInfo(name = DATA_FIELD)
    val response: String? = null
) {
    companion object {
        const val TABLE_NAME = "cache"

        const val URL_FIELD = "request_url"
        const val TIMESTAMP_FIELD = "timeStamp"
        const val DATA_FIELD = "data"
    }
}