package com.example.weather.cityWeatherForecast.currentConditions

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind (

	@SerializedName("Direction") val direction : Direction,
	@SerializedName("Speed") val speed : Speed
): Serializable