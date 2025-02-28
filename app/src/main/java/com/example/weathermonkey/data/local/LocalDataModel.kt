package com.example.weathermonkey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "local_data")
data class LocalDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
)