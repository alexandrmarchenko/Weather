package com.example.weather.cityWeatherForecast.currentConditions

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Metric (

	@SerializedName("Value") val value : Double,
	@SerializedName("Unit") val unit : String,
	@SerializedName("UnitType") val unitType : Int
): Serializable