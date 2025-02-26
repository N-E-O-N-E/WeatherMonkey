package com.example.weathermonkey

import WeatherModel
import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.example.weathermonkey.data.remote.WeatherResponse
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel(
    application: Application,
    private val weatherRepository: WeatherRepositoryInterface,
) : AndroidViewModel(application) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _temperature = MutableStateFlow<String?>(null)
    val temperature: StateFlow<String?> = _temperature.asStateFlow()

    private val _weatherDescription = MutableStateFlow<String?>(null)
    val weatherDescription: StateFlow<String?> = _weatherDescription.asStateFlow()

    private val _precipitationProbability = MutableStateFlow<String?>(null)
    val precipitationProbability: StateFlow<String?> = _precipitationProbability.asStateFlow()

    private val _currentTemperature = MutableStateFlow<String?>(null)
    val currentTemperature: StateFlow<String?> = _currentTemperature.asStateFlow()

    private val _weatherResponse = MutableStateFlow<WeatherModel?>(null)
    val weatherResponse = _weatherResponse.asStateFlow()



    suspend fun fetchTemperatureByLocation(latitude: Double, longitude: Double) {
        try {
            val weatherData = weatherRepository.fetchCurrentWeatherData(
                latitude = latitude,
                longitude = longitude,
                forecast_days = 14
            )
            _weatherResponse.value = weatherData

            val currentTemperature = weatherData.hourly.temperature2m.firstOrNull()
            _currentTemperature.value = currentTemperature?.let { "$it°C" } ?: "Keine Daten"

            val temperatureMax = weatherData.daily.temperature2mMax.firstOrNull()
            val temperatureMin = weatherData.daily.temperature2mMin.firstOrNull()

            if (temperatureMax != null && temperatureMin != null) {
                _temperature.value = "Max: $temperatureMax°C, Min: $temperatureMin°C"
            } else {
                _temperature.value = "Keine Temperaturdaten verfügbar"
            }

            val weatherCode = weatherData.hourly.weatherCode.firstOrNull()
            _weatherDescription.value = getWeatherDescriptionByCode(weatherCode)

            val precipitation = weatherData.hourly.precipitationProbability.firstOrNull()
            _precipitationProbability.value = "${precipitation ?: 0}%"

        } catch (e: Exception) {
            _temperature.value = "Fehler: ${e.message}"
            _weatherDescription.value = "Fehler: ${e.message}"
            _precipitationProbability.value = "Fehler: ${e.message}"
        }
    }

//TODO: locationRepository
    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            _location.value = location
        }.addOnFailureListener {
            _location.value = null
        }
    }

    fun getWeatherDescriptionByCode(code: Int?): String {
        return when (code) {
            0 -> "Sonnig"
            1, 2, 3 -> "Überwiegend sonnig"
            45, 48 -> "Nebel"
            51, 53, 55 -> "Leichter Nieselregen"
            61, 63, 65 -> "Regen"
            66, 67 -> "Gefrierender Regen"
            71, 73, 75 -> "Schnee"
            80, 81, 82 -> "Schauer"
            85, 86 -> "Schneeschauer"
            95 -> "Gewitter"
            96, 99 -> "Gewitter mit Hagel"
            else -> "Wetterlage unbekannt"
        }
    }

    fun getWeatherIconByCode(code: Int?): Int {
        return when (code) {
            0 -> R.drawable.suniconsmall
            1, 2, 3 -> R.drawable.cloudiconsmall
            45, 48 -> R.drawable.rainiconsmall
            51, 53, 55 -> R.drawable.rainiconsmall
            61, 63, 65 -> R.drawable.rainiconsmall
            66, 67 -> R.drawable.snowiconsmall
            71, 73, 75 -> R.drawable.snowiconsmall
            80, 81, 82 -> R.drawable.rainiconsmall
            85, 86 -> R.drawable.snowiconsmall
            95 -> R.drawable.rainiconsmall
            96, 99 -> R.drawable.rainiconsmall
            else -> R.drawable.rainiconsmall
        }
    }
}
