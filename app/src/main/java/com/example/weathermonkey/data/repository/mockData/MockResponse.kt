package com.example.weathermonkey.data.repository.mockData

import Daily
import DailyUnits
import Hourly
import HourlyUnits
import WeatherModel

val mockResponse = WeatherModel(
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
        time = listOf("2025-02-24","2025-02-24","2025-02-24","2025-02-24","2025-02-24","2025-02-24","2025-02-24",),
        temperature2mMax = listOf(6.5,6.5,6.5,6.5,6.5,6.5,6.5,),
        temperature2mMin = listOf(6.3,6.3,6.3,6.3,6.3,6.3,6.3,),
        weatherCode = listOf(3, 2),
        sunrise = listOf(
            "2025-02-27T00:00",
            "2025-02-28T06:54",
            "2025-03-01T06:51",
            "2025-03-02T06:49",
            "2025-03-03T06:47",
            "2025-03-04T06:45",
            "2025-03-05T06:42",
            "2025-03-06T06:40",
            "2025-03-07T06:38",
            "2025-03-08T06:36",
            "2025-03-09T06:33",
            "2025-03-10T06:31",
            "2025-03-11T06:29",
            "2025-03-12T06:26"
        ),
        sunset = listOf(
            "2025-02-27T17:41",
            "2025-02-28T17:43",
            "2025-03-01T17:45",
            "2025-03-02T17:47",
            "2025-03-03T17:48",
            "2025-03-04T17:50",
            "2025-03-05T17:52",
            "2025-03-06T17:54",
            "2025-03-07T17:56",
            "2025-03-08T17:57",
            "2025-03-09T17:59",
            "2025-03-10T18:01",
            "2025-03-11T18:03",
            "2025-03-12T18:05"
        )
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
        time = listOf(
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
            "2025-02-24T00:00",
            "2025-02-24T01:00",
        ),
        temperature2m = listOf(
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
            6.5,
            6.3,
        ),
        apparentTemperature = listOf(
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
            5.0,
            5.2,
        ),
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