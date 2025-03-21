package com.example.weathermonkey

import WeatherModel
import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermonkey.data.local.LocalDataModel
import com.example.weathermonkey.data.local.LocalDatabase
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import com.example.weathermonkey.data.repository.mockData.mockResponse
import com.example.weathermonkey.utils.indexedTempForCurrentHourAsString
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(
    application: Application,
    private val weatherRepository: WeatherRepositoryInterface,
) : AndroidViewModel(application) {

    private val dao = LocalDatabase.getDatabase(application.applicationContext).dao()

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _weatherResponseForecast = MutableStateFlow<WeatherModel?>(null)
    val weatherResponseForecast = _weatherResponseForecast.asStateFlow()

    private val _weatherResponseDaily = MutableStateFlow<WeatherModel?>(null)
    val weatherResponseDaily = _weatherResponseDaily.asStateFlow()


    val data = dao.getAllLocalData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


    fun getAllLocalData() {
        viewModelScope.launch {
            dao.getAllLocalData()
        }
    }

    fun saveLocalData(data: LocalDataModel) {
       viewModelScope.launch {
           dao.insertLocalData(data)
       }
    }

    fun deleteLocalData() {
        viewModelScope.launch {
            dao.deleteAllLocalData()
        }
    }

    suspend fun fetchWeatherResponseDaily(latitude: Double, longitude: Double) {
        try {
            val weatherData = weatherRepository.fetchCurrentWeatherData(
                latitude = latitude,
                longitude = longitude,
                forecast_days = 1
            )
            _weatherResponseDaily.value = weatherData
        } catch (e: Exception) {
            println("${e}")
        }
    }

    suspend fun fetchWeatherResponseForecast(latitude: Double, longitude: Double) {
        try {
            val weatherData = weatherRepository.fetchCurrentWeatherData(
                latitude = latitude,
                longitude = longitude,
                forecast_days = 14
            )
            _weatherResponseForecast.value = weatherData
        } catch (e: Exception) {
            println("${e}")
//            _temperature.value = "Fehler: ${e.message}"
//            _weatherDescription.value = "Fehler: ${e.message}"
//            _precipitationProbability.value = "Fehler: ${e.message}"
        }
    }

    fun fetchWallpaperByCode(dayState: Int?): Int {
        val result =
            if (dayState == 1) {
            getWeatherWallpaperByCode(
                indexedTempForCurrentHourAsString.getCurrentHoureAsInt(
                    weatherResponseForecast.value ?: mockResponse
                )
            )
        } else {
            R.drawable.nightimage
        }
        return result
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
            0, 1 -> "Sonnig"
            2 -> "Sonnig bewölkt"
            3 -> "Bewölkt"
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

    fun getWeatherIconXLByCode(code: Int?, isDayState: Int): Int {
        if (isDayState == 1) {
            return when (code) {
                0, 1 -> R.drawable.sunicon
                2 -> R.drawable.cloudicon
                3 -> R.drawable.cloudmaxicon
                45, 48 -> R.drawable.fogicon
                51, 53, 55 -> R.drawable.rainicon
                61, 63, 65 -> R.drawable.rainicon
                66, 67 -> R.drawable.snowicon
                71, 73, 75 -> R.drawable.snowicon
                80, 81, 82 -> R.drawable.rainicon
                85, 86 -> R.drawable.snowicon
                95 -> R.drawable.thundericon
                96, 99 -> R.drawable.thundericon
                else -> R.drawable.cloudicon
            }
        } else {
            return when (code) {
                0, 1 -> R.drawable.nightstarsicon
                2 -> R.drawable.nightcloudicon
                3 -> R.drawable.cloudmaxicon
                45, 48 -> R.drawable.fogicon
                51, 53, 55 -> R.drawable.rainicon
                61, 63, 65 -> R.drawable.rainicon
                66, 67 -> R.drawable.snowicon
                71, 73, 75 -> R.drawable.snowicon
                80, 81, 82 -> R.drawable.rainicon
                85, 86 -> R.drawable.snowicon
                95 -> R.drawable.thundericon
                96, 99 -> R.drawable.thundericon
                else -> R.drawable.nightcloudicon
            }
        }
    }

    fun getWeatherIconByCode(code: Int?, isDayState: Int?): Int {

        if (isDayState == 1) {
            return when (code) {
                0, 1 -> R.drawable.suniconsmall
                2 -> R.drawable.cloudiconsmall
                3 -> R.drawable.cloudmaxiconsmall
                45, 48 -> R.drawable.fogiconsmall
                51, 53, 55 -> R.drawable.rainiconsmall
                61, 63, 65 -> R.drawable.rainiconsmall
                66, 67 -> R.drawable.snowiconsmall
                71, 73, 75 -> R.drawable.snowiconsmall
                80, 81, 82 -> R.drawable.rainiconsmall
                85, 86 -> R.drawable.snowiconsmall
                95 -> R.drawable.thundericonsmall
                96, 99 -> R.drawable.thundericonsmall
                else -> R.drawable.cloudiconsmall
            }
        } else {
            return when (code) {
                0, 1 -> R.drawable.nightstarsiconsmall
                2 -> R.drawable.nightcloudiconsmall
                3 -> R.drawable.cloudmaxiconsmall
                45, 48 -> R.drawable.fogiconsmall
                51, 53, 55 -> R.drawable.rainiconsmall
                61, 63, 65 -> R.drawable.rainiconsmall
                66, 67 -> R.drawable.snowiconsmall
                71, 73, 75 -> R.drawable.snowiconsmall
                80, 81, 82 -> R.drawable.rainiconsmall
                85, 86 -> R.drawable.snowiconsmall
                95 -> R.drawable.thundericonsmall
                96, 99 -> R.drawable.thundericonsmall
                else -> R.drawable.nightcloudiconsmall
            }
        }
    }

    fun getWeatherWallpaperByCode(code: Int?): Int {
        Log.d("weatherCode", "test: $code")
        return when (code) {
            0, 1 -> R.drawable.sunimage
            2 -> R.drawable.suncloudimage
            3 -> R.drawable.cloudimage
            45, 48 -> R.drawable.cloudimage
            51, 53, 55 -> R.drawable.rainimage
            61, 63, 65 -> R.drawable.rainimage
            66, 67 -> R.drawable.rainimage
            71, 73, 75 -> R.drawable.snowimage
            80, 81, 82 -> R.drawable.rainimage
            85, 86 -> R.drawable.snowimage
            95 -> R.drawable.rainimage
            96, 99 -> R.drawable.rainimage
            else -> R.drawable.sunimage
        }
    }
}

