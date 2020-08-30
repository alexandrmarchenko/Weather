package com.example.weather.geo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng


class Geo(private val context: Context) {
    init {
        requestPemissions()
    }

    var currentPosition: LatLng? = null

    companion object {
        private var INSTANCE: Geo? = null

        val PERMISSION_REQUEST_CODE = 10

        fun getInstance(context: Context): Geo =
            INSTANCE
                ?: Geo(context)
                    .also { INSTANCE = it }
    }

    private fun requestPemissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Запрашиваем координаты
            requestLocation()
        } else {
            // Permission’ов нет, запрашиваем их у пользователя
            requestLocationPermissions()
        }
    }

    fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        val provider = locationManager.getBestProvider(criteria, true)

        val lastPosition = locationManager.getLastKnownLocation(provider)
        currentPosition = LatLng(lastPosition.latitude, lastPosition.longitude)
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 10000, 10f, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    val lat: Double = location.latitude // Широта
                    val lng: Double = location.longitude // Долгота
                    currentPosition = LatLng(lat, lng)
                }

                override fun onStatusChanged(
                    provider: String,
                    status: Int,
                    extras: Bundle
                ) {
                }

                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            })
        }
    }

    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ||
            !ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                context, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }
}