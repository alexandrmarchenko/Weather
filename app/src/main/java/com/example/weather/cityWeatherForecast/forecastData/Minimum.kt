package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Minimum (

	@SerializedName("Value") val value : Double,
	@SerializedName("Unit") val unit : String,
	@SerializedName("UnitType") val unitType : Int
): Serializable