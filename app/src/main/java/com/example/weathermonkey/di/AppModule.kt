package com.example.weathermonkey.di

import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.local.LocationsDatabase
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.repository.WeatherRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {

    single {
        LocationsDatabase.getDatabase(androidContext())
    }

    single {
        get<LocationsDatabase>().dao()
    }

    single {
        WeatherAPI.retrofitService
    }

    single {
        //HistoryWeatherRepositoryMock(get())
        WeatherRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}