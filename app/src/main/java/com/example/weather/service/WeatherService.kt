package com.example.weather.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.weather.DataLoader
import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import com.example.weather.cityWeatherForecast.cityData.CityData

class WeatherService : Service() {

    private val binder: IBinder = ServiceBinder()
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun loadWeatherData(cityData: CityData, locale: String, metric: Boolean): CityWeatherForecastData? {
        return DataLoader.load(cityData, locale, metric)
    }

    private fun loadWeatherData(key: Int, locale: String, metric: Boolean): CityWeatherForecastData? {
        return DataLoader.load(key, locale, metric)
    }

    private fun loadCityData(searchText: String, locale: String): ArrayList<CityData> {
        return DataLoader.loadCityData(searchText, locale)
    }


    inner class ServiceBinder: Binder() {
        fun getService(): WeatherService {
            return this@WeatherService
        }

        fun loadWeatherData(cityData: CityData, locale: String, metric: Boolean): CityWeatherForecastData? {

            return getService().loadWeatherData(cityData,locale,metric)
        }
        fun loadWeatherData(key: Int, locale: String, metric: Boolean): CityWeatherForecastData? {
            return getService().loadWeatherData(key,locale,metric)
        }

        fun loadCityData(searchText: String, locale: String): ArrayList<CityData> {
            return getService().loadCityData(searchText,locale)
        }
    }
}
