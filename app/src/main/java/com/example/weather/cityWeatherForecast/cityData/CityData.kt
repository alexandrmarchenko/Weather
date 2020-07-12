package com.example.weather.cityWeatherForecast.cityData

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CityData(

    @SerializedName("Version") val version: Int,
    @SerializedName("Key") val Key: Int,
    @SerializedName("Type") val type: String,
    @SerializedName("Rank") val rank: Int,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("EnglishName") val englishName: String,
    @SerializedName("PrimaryPostalCode") val primaryPostalCode: String,
    @SerializedName("Region") val region: Region,
    @SerializedName("Country") val country: Country,
    @SerializedName("AdministrativeArea") val administrativeArea: AdministrativeArea
) : Serializable