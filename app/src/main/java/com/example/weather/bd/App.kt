package com.example.weather.bd

import android.app.Application
import androidx.room.Room
import com.example.weather.bd.database.WeatherDatabase

class App : Application() {
    companion object {
        private var instance: App? = null

        fun getInstance() = instance
    }

    private var db: WeatherDatabase? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weather"
        )
            .allowMainThreadQueries()
            .build()
    }

    fun getWeatherDao() = db?.getWeatherDao()
}