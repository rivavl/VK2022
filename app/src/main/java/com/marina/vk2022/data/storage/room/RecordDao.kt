package com.marina.vk2022.data.storage.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordsDao {
    @Insert
    suspend fun insertRecord(recordItem: RoomRecordItem)

    @Delete
    suspend fun removeRecord(recordItem: RoomRecordItem)

    @Delete
    suspend fun removeRecord(recordItems: List<RoomRecordItem>)

    @Query("SELECT * FROM records")
    suspend fun getAllRecords(): List<RoomRecordItem>

    @Update
    suspend fun update(recordItem: RoomRecordItem)

}