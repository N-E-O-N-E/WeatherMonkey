package com.example.weathermonkey.data.remote

import WeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://archive-api.open-meteo.com/"

// Muss komplett in App-Modul (KOIN) liegen

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface APIService {
    @GET("v1/archive?latitude=49.443&longitude=7.7716&start_date=2025-02-24&end_date=2025-02-24&hourly=temperature_2m&timezone=auto")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
    ): WeatherModel

    @GET("v1/forecast?latitude=49.443&longitude=7.7716&hourly=temperature_2m&timezone=auto&forecast_days=1")
    suspend fun getCurrentWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("forecast_days") forecast_days: Int,
    ): WeatherModel

}

object WeatherAPI {
    val retrofitService: APIService by lazy { retrofit.create(APIService::class.java) }
}