package com.example.weathermonkey.data.local

import CurrentWeatherModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrentWeatherModel::class], version = 1, exportSchema = false)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun dao(): LocationsDao

    companion object {
        @Volatile
        private var instance: LocationsDatabase? = null

        fun getDatabase(context: Context): LocationsDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LocationsDatabase::class.java,
                    "locations_db"
                ).build().also { instance = it }
            }
        }
    }
}
