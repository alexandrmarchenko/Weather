package com.example.weather.cityWeatherForecast

import com.example.weather.cityWeatherForecast.cityData.CityData
import com.example.weather.cityWeatherForecast.currentConditions.CurrentConditionsData
import com.example.weather.cityWeatherForecast.forecastData.ForecastData
import java.io.Serializable

class CityWeatherForecastData(
    val city: CityData?,
    var currentConditions: CurrentConditionsData?,
    var weatherForecast: ForecastData?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (other is CityWeatherForecastData) {
            if (other.city?.Key == this.city?.Key) {
                return true
            }
        }
        return false
    }
}