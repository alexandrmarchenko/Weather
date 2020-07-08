package com.example.weather

import java.io.Serializable

class CityData(
    city: String,
    country: String,
    administrativeArea: String
) : Serializable {

    var city: String

    init {
        this.city = city
    }

    var country: String

    init {
        this.country = country
    }

    var administrativeArea: String

    init {
        this.administrativeArea = administrativeArea
    }

    fun getAministrativeInfo() = "$administrativeArea, $country"
}