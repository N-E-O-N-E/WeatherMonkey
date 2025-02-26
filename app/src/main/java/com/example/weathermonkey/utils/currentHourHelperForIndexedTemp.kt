package com.example.weathermonkey.utils

import WeatherModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object indexedTempForCurrentHourAsString {
    fun getCurrent(): String {
        val currentHourPattern: DateTimeFormatter? = DateTimeFormatter.ofPattern("HH")
        val currentHour = LocalTime.now().format(currentHourPattern)
        return currentHour
    }
}