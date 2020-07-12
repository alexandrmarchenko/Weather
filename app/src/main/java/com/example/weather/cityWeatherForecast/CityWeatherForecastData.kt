package com.example.weather.cityWeatherForecast

import com.example.weather.cityWeatherForecast.cityData.CityData
import com.example.weather.cityWeatherForecast.currentConditions.CurrentConditionsData
import com.example.weather.cityWeatherForecast.forecastData.ForecastData
import java.io.Serializable

class CityWeatherForecastData(
    val city: CityData?,
    var currentConditions: CurrentConditionsData?,
    var weatherForecast: ForecastData?
) : Serializable