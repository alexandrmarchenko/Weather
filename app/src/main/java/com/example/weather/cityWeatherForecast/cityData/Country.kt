package com.example.weather.cityWeatherForecast.cityData

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Country (

	@SerializedName("ID") val iD : String,
	@SerializedName("LocalizedName") val localizedName : String,
	@SerializedName("EnglishName") val englishName : String
): Serializable