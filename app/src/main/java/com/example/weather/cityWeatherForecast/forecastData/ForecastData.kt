package com.example.weather.cityWeatherForecast.forecastData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastData (

	@SerializedName("Headline") val headline : Headline,
	@SerializedName("DailyForecasts") val dailyForecasts : List<DailyForecasts>
): Serializable