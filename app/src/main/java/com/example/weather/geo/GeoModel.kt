package com.example.weather.geo

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class GeoModel(
    private val addressLiveDataForViewToObserve: MutableLiveData<Address?> = MutableLiveData()
) : ViewModel() {

    fun getAdressData(): LiveData<Address?> {
        return addressLiveDataForViewToObserve
    }

    fun getAdress(context: Context, position: LatLng) {
        val geocoder = Geocoder(context)
        val addresses: List<Address> =
            geocoder.getFromLocation(position.latitude, position.longitude, 1)
        if (addresses.isNotEmpty()) {
            addressLiveDataForViewToObserve.value = addresses[0]
        } else {
            addressLiveDataForViewToObserve.value = null
        }
    }

}