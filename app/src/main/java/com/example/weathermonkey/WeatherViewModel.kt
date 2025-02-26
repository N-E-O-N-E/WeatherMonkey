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

    private val _temperature = MutableLiveData<String?>()
    var temperature = _temperature

//    init {
//        if (Manifest.permission.ACCESS_FINE_LOCATION == "isGranted" || Manifest.permission.ACCESS_COARSE_LOCATION == "isGranted") { //TODO: make sure this works if permissions have not been set yet
//            fetchLocation()
//            viewModelScope.launch {
//                _temperature.value = listOf(getCurrentWeather(1))
//            }
//        }
//    }

    suspend fun fetchTemperatureByLocation(latitude: Double, longitude: Double) {
        try {

            val weatherData = weatherRepository.fetchCurrentWeatherData(
                latitude = latitude,
                longitude = longitude,
                forecast_days = 1
            )

            val temperatureMax = weatherData.daily.temperature2mMax.firstOrNull()
            val temperatureMin = weatherData.daily.temperature2mMin.firstOrNull()

            if (temperatureMax != null && temperatureMin != null) {
                _temperature.postValue("Max: $temperatureMax°C, Min: $temperatureMin°C")
            } else {
                _temperature.postValue("Keine Temperaturdaten verfügbar")
                println("Temperature data not available in API response")
            }

        } catch (e: Exception) {
            _temperature.postValue("Fehler: ${e.message}")
            println("Fehler beim Abrufen der Wetterdaten: ${e.message}")
        }
    }



    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                _location.postValue(location)
            } else {
                _location.postValue(null) //TODO: If else kürzen
            }
        }.addOnFailureListener {
            _location.postValue(null)
        }
    }

}