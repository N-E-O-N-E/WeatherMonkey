package com.example.weathermonkey.di

import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.local.LocalDao
import com.example.weathermonkey.data.local.LocalDatabase
import com.example.weathermonkey.data.remote.WeatherAPI
import com.example.weathermonkey.data.repository.WeatherRepositoryImpl
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single {
        WeatherAPI.retrofitService
    }

    single<LocalDatabase> {
        LocalDatabase.getDatabase(androidContext())
    }

    single<LocalDao> {
        get<LocalDatabase>().dao()
    }

    single<WeatherRepositoryInterface> {
        WeatherRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}