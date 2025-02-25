package com.example.weathermonkey.di

import com.example.weathermonkey.WeatherViewModel
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
            .create(WeatherAPI::class.java)
    }

    single {
        LocationsDatabase.getDatabase(androidContext())
    }

    single {
        get<LocationsDatabase>().dao()
    }

    single<WeatherRepositoryInterface> {
        WeatherRepositoryImpl(get())
    }

    viewModelOf(::WeatherViewModel)
}