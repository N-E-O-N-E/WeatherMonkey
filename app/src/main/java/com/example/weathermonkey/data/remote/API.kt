package com.example.weathermonkey.data.remote

import WeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.open-meteo.com/"

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface APIService {
    @GET("v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
    ): WeatherModel

    @GET("v1/forecast")
    suspend fun getCurrentWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("forecast_days") forecast_days: Int,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code,sunrise,sunset",
        @Query("hourly") hourly: String = "temperature_2m,apparent_temperature,precipitation_probability,rain,showers,snowfall,weather_code,cloud_cover_low,cloud_cover_mid,cloud_cover_high,is_day",
        @Query("timezone") timezone: String = "auto"
    ): WeatherModel

}

object WeatherAPI {
    val retrofitService: APIService by lazy { retrofit.create(APIService::class.java) }
}