package com.example.weather.cityWeatherForecast.currentConditions

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrentConditionsData(
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("RelativeHumidity") val relativeHumidity: Int,
    @SerializedName("Wind") val wind: Wind,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String
) : Serializable