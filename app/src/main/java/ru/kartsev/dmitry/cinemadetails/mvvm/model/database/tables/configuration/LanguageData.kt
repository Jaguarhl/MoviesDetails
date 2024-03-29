package ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData.Companion.ID_FIELD
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData.Companion.ISO_CODE_FIELD
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.configuration.LanguageData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys= [ ID_FIELD, ISO_CODE_FIELD])
data class LanguageData(
    @ColumnInfo(name = ID_FIELD) var id: Long = 0,
    @ColumnInfo(name = ENGLISH_NAME_FIELD) var englishName: String? = null,
    @ColumnInfo(name = ISO_CODE_FIELD) var isoCode: String,
    @ColumnInfo(name = NAME_FIELD)var name: String? = null
) {
    companion object {
        const val TABLE_NAME = "languages"

        const val ID_FIELD = "id"
        const val ENGLISH_NAME_FIELD = "english_name"
        const val ISO_CODE_FIELD = "iso_639_1"
        const val NAME_FIELD = "name"
    }
}