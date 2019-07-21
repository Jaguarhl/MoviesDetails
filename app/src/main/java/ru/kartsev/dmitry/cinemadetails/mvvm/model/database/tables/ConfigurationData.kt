package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.DatabaseConverters
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.ConfigurationData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ConfigurationData(
    @PrimaryKey
    @ColumnInfo(name = TIMESTAMP_FIELD) var timeStamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = BASE_URL_FIELD) var baseUrl: String? = null,
    @ColumnInfo(name = SECURE_BASE_URL_FIELD) var secureBaseUrl: String? = null,

    @ColumnInfo(name = CHANGE_KEYS_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var changeKeys: List<String>? = null,

    @ColumnInfo(name = BACKDROP_SIZES_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var backdropSizes: List<String>? = null,

    @ColumnInfo(name = LOGO_SIZES_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var logoSizes: List<String>? = null,

    @ColumnInfo(name = POSTER_SIZES_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var posterSizes: List<String>? = null,

    @ColumnInfo(name = PROFILE_SIZES_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var profileSizes: List<String>? = null,

    @ColumnInfo(name = STILL_SIZES_FIELD)
    @TypeConverters(DatabaseConverters::class)
    var stillSizes: List<String>? = null
) {
    companion object {
        const val TABLE_NAME = "configuration_table"

        const val TIMESTAMP_FIELD = "last_updated"

        const val BASE_URL_FIELD = "base_url_keys"
        const val SECURE_BASE_URL_FIELD = "secure_base_url_keys"
        const val CHANGE_KEYS_FIELD = "change_keys"
        const val BACKDROP_SIZES_FIELD = "backdrop_sizes"
        const val LOGO_SIZES_FIELD = "logo_sizes"
        const val POSTER_SIZES_FIELD = "poster_sizes"
        const val PROFILE_SIZES_FIELD = "profile_sizes"
        const val STILL_SIZES_FIELD = "still_sizes"
    }
}