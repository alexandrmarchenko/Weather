package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyForecasts(

    @SerializedName("Date") val date: String,
    @SerializedName("EpochDate") val epochDate: Int,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("Day") val day: Day,
    @SerializedName("Night") val night: Night,
    @SerializedName("MobileLink") val mobileLink: String
) : Serializable