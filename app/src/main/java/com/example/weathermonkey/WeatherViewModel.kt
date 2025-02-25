package com.example.weathermonkey

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface

class WeatherViewModel(
    application: Application,
    private val historyWeatherRepository: WeatherRepositoryInterface,
    ) : AndroidViewModel(application) {




}