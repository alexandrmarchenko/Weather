package com.example.weather

import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import java.io.Serializable
import java.util.*

class WeatherData : Serializable {

    companion object {
        val instance = WeatherData()
    }

    val items: ArrayList<CityWeatherForecastData> = ArrayList()
        get() = field


    fun get(index: Int): CityWeatherForecastData {
        return items[index]
    }

    fun add(item: CityWeatherForecastData) {
        if (!items.contains(item)) {
            items.add(item)
        }
    }

}