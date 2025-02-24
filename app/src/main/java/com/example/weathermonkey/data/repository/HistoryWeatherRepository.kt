package com.example.weathermonkey.data.repository

import HistoryWeatherModel
import com.example.weathermonkey.data.remote.WeatherAPI

interface HistoryWeatherRepositoryInterface {

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String,
        ): HistoryWeatherModel
}

class HistoryWeatherRepositoryImpl(private val apiService: WeatherAPI) : HistoryWeatherRepositoryInterface {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String
    ): HistoryWeatherModel {
        TODO("Not yet implemented")
    }

}