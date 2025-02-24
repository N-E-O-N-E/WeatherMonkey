package com.example.weathermonkey.data.repository

import com.example.weathermonkey.data.remote.WeatherAPI

interface HistoryWeatherRepositoryInterface {

}

class HistoryWeatherRepositoryImpl(private val apiService: WeatherAPI) : HistoryWeatherRepositoryInterface {

}