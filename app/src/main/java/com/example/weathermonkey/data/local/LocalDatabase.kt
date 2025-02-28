package com.example.weathermonkey.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalDataModel::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dao(): LocalDao
    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "locations_db"
                ).build().also { instance = it }
            }
        }
    }
}
