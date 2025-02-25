package com.example.weathermonkey.utils

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local


object DatePlusOneWeekHelper {
    val startDate = LocalDate.now()
    val endDate = startDate.plusWeeks(1)
    val startDateString = LocalDate.now().toString()
    val endDateString = startDate.plusWeeks(1).toString()
}