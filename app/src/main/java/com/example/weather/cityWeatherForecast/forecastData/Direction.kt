package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Direction (

	@SerializedName("Degrees") val degrees : Int,
	@SerializedName("Localized") val localized : String,
	@SerializedName("English") val english : String
): Serializable