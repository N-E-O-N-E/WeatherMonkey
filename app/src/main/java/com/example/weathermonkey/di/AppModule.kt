package com.example.weathermonkey.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.local.LocationsDao
import com.example.weathermonkey.data.local.LocationsDatabase
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.repository.HistoryWeatherRepositoryImpl
import com.example.weathermonkey.data.repository.HistoryWeatherRepositoryInterface
import org.koin.core.module.dsl.viewModelOf


val appModule = module {

    single<LocationsDatabase> {
        LocationsDatabase.getDatabase(androidContext())
    }

    single<LocationsDao> {
        get<LocationsDatabase>().dao()
    }

    single {
        WeatherAPI.retrofitService
    }

    single<HistoryWeatherRepositoryInterface> {
        HistoryWeatherRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}