package com.example.weathermonkey

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weathermonkey.data.repository.HistoryWeatherRepositoryInterface

class WeatherViewModel(
    application: Application,
    private val historyWeatherRepository: HistoryWeatherRepositoryInterface,
    ) : AndroidViewModel(application) {



}