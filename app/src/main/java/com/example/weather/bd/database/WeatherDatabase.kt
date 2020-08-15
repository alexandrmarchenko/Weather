package com.example.weather.bd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.bd.dao.WeatherDao
import com.example.weather.bd.model.CitySearchHist
import com.example.weather.bd.model.DateConverter

@Database(entities = [CitySearchHist::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}