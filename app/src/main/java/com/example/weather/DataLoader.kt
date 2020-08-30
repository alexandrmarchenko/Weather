package com.example.weather

import android.os.Handler
import android.util.Log
import com.example.weather.cityWeatherForecast.CityWeatherForecastData
import com.example.weather.cityWeatherForecast.cityData.CityData
import com.example.weather.cityWeatherForecast.currentConditions.CurrentConditionsData
import com.example.weather.cityWeatherForecast.forecastData.ForecastData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread


class DataLoader() {

    companion object {
        private val BASE_URL = "http://dataservice.accuweather.com"
        private val TAG = "DATA_LOADER"
        fun loadCityData(searchText: String, locale: String): ArrayList<CityData> {

            val mHandler = Handler()
            var cityRequest: ArrayList<CityData> = ArrayList()

            try {
                val param = URLEncoder.encode(searchText, "utf-8")
                var uri = URL(
                    "$BASE_URL/locations/v1/cities/search?apikey=${BuildConfig.WEATHER_API_KEY}&q=" + param + "&language=$locale&details=false"
                )
                thread {
                    var urlConnection: HttpURLConnection? = null
                    try {
                        urlConnection = uri.openConnection() as HttpURLConnection

                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        var collectionType = object : TypeToken<ArrayList<CityData>>() {}.type
                        cityRequest = gson.fromJson<ArrayList<CityData>>(text, collectionType)

                    } catch (e: Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                }.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }

            return cityRequest
        }

        private fun loadCityData(key: Int, locale: String): CityData? {

            val mHandler = Handler()
            var uri = URL(
                "$BASE_URL/locations/v1/$key?apikey=" + BuildConfig.WEATHER_API_KEY
                        + "&language=$locale&details=false"
            )
            var cityRequest: CityData? = null
            try {
                val thread = Thread(Runnable {
                    var urlConnection: HttpURLConnection? = null
                    try {
                        urlConnection = uri.openConnection() as HttpURLConnection
                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        cityRequest = gson.fromJson(text, CityData::class.java)

                    } catch (e: Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                })
                thread.start()
                thread.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }

            return cityRequest
        }

        private fun loadCityData(geo: LatLng, locale: String): CityData? {
            val mHandler = Handler()
            val param = URLEncoder.encode("${geo.latitude},${geo.longitude}", "utf-8")
            var uri = URL(
                "$BASE_URL/locations/v1/cities/geoposition/search/?apikey=" + BuildConfig.WEATHER_API_KEY + "q=$param"
                        + "&language=$locale&details=false"
            )
            var cityRequest: CityData? = null
            try {
                val thread = Thread(Runnable {
                    var urlConnection: HttpURLConnection? = null
                    try {
                        urlConnection = uri.openConnection() as HttpURLConnection
                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        cityRequest = gson.fromJson(text, CityData::class.java)

                    } catch (e: Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                })
                thread.start()
                thread.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }

            return cityRequest
        }

        private fun loadForecast(key: Int, locale: String, metric: Boolean): ForecastData? {
            val mHandler = Handler()

            var weatherRequest: ForecastData? = null
            try {
                var uri = URL(
                    "$BASE_URL/forecasts/v1/daily/5day/$key?apikey=" + BuildConfig.WEATHER_API_KEY
                            + "&language=$locale&details=true&metric=$metric"
                )
                thread {
                    var urlConnection: HttpURLConnection? = null

                    try {

                        urlConnection = uri.openConnection() as HttpURLConnection
                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        weatherRequest = gson.fromJson(text, ForecastData::class.java)

                    } catch (e: java.lang.Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                }.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }

            return weatherRequest
        }

        private fun loadCurrentCondition(key: Int, locale: String): CurrentConditionsData? {
            val mHandler = Handler()
            var conditionRequest = ArrayList<CurrentConditionsData>()
            var uri = URL(
                "$BASE_URL/currentconditions/v1/$key?apikey=" + BuildConfig.WEATHER_API_KEY
                        + "&language=$locale&details=true"
            )
            try {
                var urlConnection: HttpURLConnection? = null
                thread {

                    try {

                        urlConnection = uri.openConnection() as HttpURLConnection

                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        var collectionType =
                            object : TypeToken<List<CurrentConditionsData>>() {}.type
                        conditionRequest =
                            gson.fromJson<ArrayList<CurrentConditionsData>>(text, collectionType)

                    } catch (e: java.lang.Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                }.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }
            if (conditionRequest.count() > 0)
                return conditionRequest[0]
            return null
        }

        fun updateCurrentCondition(item: CityWeatherForecastData, locale: String) {
            val mHandler = Handler()
            var conditionRequest = ArrayList<CurrentConditionsData>()



            try {
                var uri = URL(
                    "$BASE_URL/currentconditions/v1/${item.city?.Key}?apikey=" + BuildConfig.WEATHER_API_KEY
                            + "&language=$locale&details=true"
                )
                var urlConnection: HttpURLConnection? = null
                thread {
                    try {
                        urlConnection = uri.openConnection() as HttpURLConnection

                        urlConnection?.requestMethod = "GET"
                        urlConnection?.readTimeout = 10000

                        var text =
                            urlConnection?.inputStream?.bufferedReader()
                                ?.use(BufferedReader::readText)

                        var gson = Gson()

                        var collectionType =
                            object : TypeToken<List<CurrentConditionsData>>() {}.type
                        conditionRequest =
                            gson.fromJson<ArrayList<CurrentConditionsData>>(text, collectionType)
                        mHandler.post {
                            item.currentConditions = conditionRequest[0]
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                        mHandler.post {
                            throw e
                        }
                    } finally {
                        urlConnection?.disconnect()
                    }
                }.join()
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                throw e
            }
        }

        fun load(key: Int, locale: String, metric: Boolean): CityWeatherForecastData? {

            try {
                var cityData: CityData?
                var currentCondition: CurrentConditionsData?
                var weatherForecast: ForecastData?

                cityData = loadCityData(key, locale)
                currentCondition = loadCurrentCondition(key, locale)
                weatherForecast = loadForecast(key, locale, metric)

                if (cityData == null || currentCondition == null || weatherForecast == null)
                    return null

                return CityWeatherForecastData(
                    cityData,
                    currentCondition,
                    weatherForecast
                )
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
            }
            return null
        }

        fun load(geo: LatLng, locale: String, metric: Boolean): CityWeatherForecastData? {

            try {
                var cityData: CityData?
                var currentCondition: CurrentConditionsData?
                var weatherForecast: ForecastData?

                cityData = loadCityData(geo, locale)
                if (cityData?.Key != null) {
                    currentCondition = loadCurrentCondition(cityData?.Key, locale)
                    weatherForecast = loadForecast(cityData?.Key, locale, metric)
                    if (cityData != null || currentCondition != null || weatherForecast != null) {
                        return CityWeatherForecastData(
                                cityData,
                        currentCondition,
                        weatherForecast
                        )
                    }

                }
                return null
            } catch (e: Exception) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
            }
            return null
        }

        fun load(cityData: CityData, locale: String, metric: Boolean): CityWeatherForecastData? {

            try {
                val key = cityData.Key
                var currentCondition: CurrentConditionsData?
                var weatherForecast: ForecastData?

                currentCondition = loadCurrentCondition(key, locale)
                weatherForecast = loadForecast(key, locale, metric)

                if (cityData == null || currentCondition == null || weatherForecast == null)
                    return null

                return CityWeatherForecastData(
                    cityData,
                    currentCondition,
                    weatherForecast
                )
            } catch (e: Exception) {
                throw e
            }
            return null
        }
    }
}