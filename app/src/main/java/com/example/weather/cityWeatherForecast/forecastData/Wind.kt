package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind (

	@SerializedName("Speed") val speed : Speed,
	@SerializedName("Direction") val direction : Direction
): Serializable