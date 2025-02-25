package com.example.weathermonkey.data.repository

import Daily
import DailyUnits
import HistoryWeatherModel
import com.example.weathermonkey.data.remote.WeatherAPI

interface HistoryWeatherRepositoryInterface {

    suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String,
    ): HistoryWeatherModel
}

class HistoryWeatherRepositoryMock(private val apiService: WeatherAPI) :
    HistoryWeatherRepositoryInterface {
    override suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String
    ): HistoryWeatherModel {
        val result = HistoryWeatherModel(
            latitude = 53.5,
            longitude = 9.9,
            generationtimeMs = 6.7,
            utcOffsetSeconds = 8,
            timezone = "Europe/Berlin",
            timezoneAbbreviation = "GMT+1",
            elevation = 241.0,
            dailyUnits = DailyUnits(
                time = "iso8601",
                temperature2mMax = "°C",
                temperature2mMin = "°C"
            ),
            daily = Daily(
                time = listOf("2025-02-24"),
                temperature2mMax = listOf(6.5),
                temperature2mMin = listOf(6.3)
            )
        )
        return result
    }

}


class HistoryWeatherRepositoryImpl(private val apiService: WeatherAPI) :
    HistoryWeatherRepositoryInterface {
    override suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String
    ): HistoryWeatherModel {
        return apiService.retrofitService.getWeatherData(
            latitude = latitude,
            longitude = longitude,
            start_date = start_date,
            end_date = end_date
        )
    }
}