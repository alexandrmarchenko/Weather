package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Night (

	@SerializedName("Icon") val icon : Int,
	@SerializedName("IconPhrase") val iconPhrase : String,
	@SerializedName("HasPrecipitation") val hasPrecipitation : Boolean,
	@SerializedName("ShortPhrase") val shortPhrase : String,
	@SerializedName("LongPhrase") val longPhrase : String,
	@SerializedName("Wind") val wind : Wind,
	@SerializedName("TotalLiquid") val totalLiquid : TotalLiquid,
	@SerializedName("CloudCover") val cloudCover : Double
): Serializable