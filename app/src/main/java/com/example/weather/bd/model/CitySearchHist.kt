package com.example.weather.bd.model

import androidx.room.*
import java.util.*

@Entity(indices = [Index(
    value = [ "search_text" ],
    unique = true
)])
data class CitySearchHist (
    @ColumnInfo(name = "search_text") val searchText: String,

    @TypeConverters(DateConverter::class)
    val date: Date
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}