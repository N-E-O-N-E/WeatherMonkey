package com.example.weathermonkey.di

import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.local.LocationsDao
import com.example.weathermonkey.data.local.LocationsDatabase
import com.example.weathermonkey.data.remote.APIService
import com.example.weathermonkey.data.remote.BASE_URL
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.repository.WeatherRepositoryImpl
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // API-Initialisierung ( Moshi, Retrofit(API-Service), WeatherAPI Object
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(BASE_URL)
            .build()
            .create(APIService::class.java)
    }
    single {
        WeatherAPI
    }

    // Datenbank-Initialisierung
    single<LocationsDatabase> {
        LocationsDatabase.getDatabase(androidContext())
    }
    single<LocationsDao> {
        get<LocationsDatabase>().dao()
    }

    // Repository-Initialisierung
    single<WeatherRepositoryInterface> {
        WeatherRepositoryImpl(get())
    }

    // ViewModel-Initialisierung
    viewModelOf(::WeatherViewModel)
}