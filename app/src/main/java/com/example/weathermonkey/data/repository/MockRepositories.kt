package com.example.weathermonkey.data.repository

import Daily
import DailyUnits
import Hourly
import HourlyUnits
import WeatherModel
import com.example.weathermonkey.data.remote.WeatherAPI

class WeatherRepositoryMock(private val apiService: WeatherAPI) :
    WeatherRepositoryInterface {
    override suspend fun fetchCurrentWeatherData(
        latitude: Double,
        longitude: Double,
        forecast_days: Int
    ): WeatherModel {
        val result = WeatherModel(
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
            ),
            hourlyUnits = HourlyUnits(
                time = "iso8601",
                temperature2m = "°C",
                apparentTemperature = "°C",
                precipitationProbability = "%",
                rain = "mm",
                showers = "mm",
                snowfall = "cm",
                weatherCode = "wmo code",
                cloudCoverLow = "%",
                cloudCoverMid = "%",
                cloudCoverHigh = "%"
            ),
            hourly = Hourly(
                time = listOf("2025-02-24T00:00", "2025-02-24T01:00"),
                temperature2m = listOf(6.5, 6.3),
                apparentTemperature = listOf(5.0, 5.2),
                precipitationProbability = listOf(20, 15),
                rain = listOf(0.0, 0.1),
                showers = listOf(0.0, 0.0),
                snowfall = listOf(0.0, 0.0),
                weatherCode = listOf(3, 2),
                cloudCoverLow = listOf(50, 60),
                cloudCoverMid = listOf(70, 80),
                cloudCoverHigh = listOf(90, 100)
            )
        )
        return result
    }

    override suspend fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        start_date: String,
        end_date: String
    ): WeatherModel {
        val result = WeatherModel(
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
            ),
            hourlyUnits = HourlyUnits(
                time = "iso8601",
                temperature2m = "°C",
                apparentTemperature = "°C",
                precipitationProbability = "%",
                rain = "mm",
                showers = "mm",
                snowfall = "cm",
                weatherCode = "wmo code",
                cloudCoverLow = "%",
                cloudCoverMid = "%",
                cloudCoverHigh = "%"
            ),
            hourly = Hourly(
                time = listOf("2025-02-24T00:00", "2025-02-24T01:00"),
                temperature2m = listOf(6.5, 6.3),
                apparentTemperature = listOf(5.0, 5.2),
                precipitationProbability = listOf(20, 15),
                rain = listOf(0.0, 0.1),
                showers = listOf(0.0, 0.0),
                snowfall = listOf(0.0, 0.0),
                weatherCode = listOf(3, 2),
                cloudCoverLow = listOf(50, 60),
                cloudCoverMid = listOf(70, 80),
                cloudCoverHigh = listOf(90, 100)
            )
        )
        return result
    }
}