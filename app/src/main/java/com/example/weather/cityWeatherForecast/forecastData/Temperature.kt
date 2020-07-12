package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Temperature (

	@SerializedName("Minimum") val minimum : Minimum,
	@SerializedName("Maximum") val maximum : Maximum
): Serializable