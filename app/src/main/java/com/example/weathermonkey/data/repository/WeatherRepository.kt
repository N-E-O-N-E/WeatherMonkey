package com.example.weathermonkey.data.repository

import WeatherModel
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.remote.WeatherResponse

interface WeatherRepositoryInterface {

    suspend fun fetchCurrentWeatherData(
        latitude: Double,
        longitude: Double,
        forecast_days: Int,
    ): WeatherModel

    suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String,
    ): WeatherModel
}

class WeatherRepositoryImpl(private val apiService: WeatherAPI) :
    WeatherRepositoryInterface {

    override suspend fun fetchCurrentWeatherData(
        latitude: Double,
        longitude: Double,
        forecast_days: Int
    ): WeatherModel {
        return apiService.retrofitService.getCurrentWeatherData(
            latitude = latitude,
            longitude = longitude,
            forecast_days = forecast_days
        )
    }

    override suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String
    ): WeatherModel {
        return apiService.retrofitService.getWeatherData(
            latitude = latitude,
            longitude = longitude,
            start_date = start_date,
            end_date = end_date
        )
    }
}


