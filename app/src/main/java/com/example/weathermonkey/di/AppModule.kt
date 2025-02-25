package com.example.weathermonkey.di

import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.local.LocationsDao
import com.example.weathermonkey.data.local.LocationsDatabase
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.repository.WeatherRepositoryImpl
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


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

    single<WeatherRepositoryInterface> {
        //HistoryWeatherRepositoryMock(get())
        WeatherRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}