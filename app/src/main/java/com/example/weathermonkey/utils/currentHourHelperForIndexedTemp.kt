package com.example.weathermonkey.utils

import WeatherModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object indexedTempForCurrentHourAsString {

    fun getCurrent(data: WeatherModel): String {
        val currentHourPattern: DateTimeFormatter? = DateTimeFormatter.ofPattern("H")
        val currentHour = LocalTime.now().format(currentHourPattern)
        val index = currentHour.toInt()

        return "${data.hourly.temperature2m.getOrNull(index) ?: "N/A"}°"

    }
}