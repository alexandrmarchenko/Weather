package com.example.weather.cityWeatherForecast.currentConditions

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Speed (

	@SerializedName("Metric") val metric : Metric,
	@SerializedName("Imperial") val imperial : Imperial
): Serializable