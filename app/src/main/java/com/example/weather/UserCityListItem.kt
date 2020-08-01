package com.example.weather

import java.io.Serializable
import kotlin.math.roundToInt

class UserCityListItem(
    city: String?,
    curTemp: Double?,
    humidity: Int?,
    windDir: String?,
    windSpeed: Double?,
    dayTemp: Double?,
    nightTemp: Double?
) : Serializable {
    var curTemp: Int

    init {
        this.curTemp = curTemp?.roundToInt() ?: 0
    }

    var city: String

    init {
        this.city = city ?: ""
    }

    var humidity: Int

    init {
        this.humidity = humidity ?: 0
    }

    var windDir: String

    init {
        this.windDir = windDir ?: ""
    }

    var windSpeed: Double

    init {
        this.windSpeed = windSpeed ?: 0.0
    }

    var dayTemp: Int

    init {
        this.dayTemp = dayTemp?.roundToInt() ?: 0
    }

    var nightTemp: Int

    init {
        this.nightTemp = nightTemp?.roundToInt() ?: 0
    }
}

