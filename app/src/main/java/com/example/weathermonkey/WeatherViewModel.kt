package com.example.weathermonkey

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class WeatherViewModel(
    application: Application,
    private val weatherRepository: WeatherRepositoryInterface,
) : AndroidViewModel(application) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            _location.postValue(location)
        }.addOnFailureListener {
            _location.postValue(null)
        }
    }


}