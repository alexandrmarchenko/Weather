package com.example.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        var citiesData: ArrayList<WeatherData> = ArrayList()
            set(value) {
                citiesData.clear()
                field = value
            }

        fun addCityData(city: String) {
            for (item in citiesData) {
                if (item.city == city)
                    return
            }
            addCity(city)
        }

        private fun addCity(vararg cities: String) {
            for (city in cities) {
                var curTemp = 17
                var humidity = 69
                var winDir = "С-В"
                var windSpeed = 13.0
                var dayTemp = 17
                var nightTemp = 12
                citiesData.add(
                    WeatherData(
                        city,
                        curTemp,
                        humidity,
                        winDir,
                        windSpeed,
                        dayTemp,
                        nightTemp
                    )
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {

            getCityWeatherForecast()

            var mainFragment = MainFragment.create(citiesData[0])

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .commit()
        }

    }

    private fun getCityWeatherForecast() {
        var cities = resources.getStringArray(R.array.user_city_list)
        addCity(*cities)
    }

}
