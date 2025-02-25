package com.example.weathermonkey.data.local

import Daily
import DailyUnits
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class LocationData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
)