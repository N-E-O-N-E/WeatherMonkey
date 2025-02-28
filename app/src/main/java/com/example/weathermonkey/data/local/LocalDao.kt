package com.example.weathermonkey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalData(data: LocalDataModel)

    @Query("SELECT * FROM local_data")
    fun getAllLocalData(): Flow<List<LocalDataModel>>

    @Query("DELETE FROM local_data")
    suspend fun deleteAllLocalData()
}