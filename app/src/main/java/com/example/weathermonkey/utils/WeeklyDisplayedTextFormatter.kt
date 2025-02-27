package com.example.weathermonkey.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter


val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val weekDays = listOf("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa")

fun WeeklyDisplayedTextFormatter(index: Int, dateString: String, dateFormatter: DateTimeFormatter, weekDays: List<String>): String {
    return if (index == 0) {
        "Heute"
    } else {
        val date = LocalDate.parse(dateString, dateFormatter)
        weekDays[date.dayOfWeek.value % 7]
    }
}
