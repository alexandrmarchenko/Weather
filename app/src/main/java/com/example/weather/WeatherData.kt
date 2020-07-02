package com.example.weather

import java.io.Serializable

class WeatherData(
    city: String,
    curTemp: Int,
    humidity: Int,
    windDir: String,
    windSpeed: Double,
    dayTemp: Int,
    nightTemp: Int
) : Serializable {

    var curTemp: Int

    init {
        this.curTemp = curTemp
    }

    var city: String

    init {
        this.city = city
    }

    var humidity: Int

    init {
        this.humidity = humidity
    }

    var windDir: String

    init {
        this.windDir = windDir
    }

    var windSpeed: Double

    init {
        this.windSpeed = windSpeed
    }

    var dayTemp: Int

    init {
        this.dayTemp = dayTemp
    }

    var nightTemp: Int

    init {
        this.nightTemp = nightTemp
    }
}