package com.marina.vk2022.data.storage.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordsDao {
    @Insert
    fun insertRecord(recordItem: RoomRecordItem)

    @Query("SELECT * FROM records WHERE recordId =:id")
    fun getRecordById(id: Long?): RoomRecordItem?

    @Query("DELETE FROM records")
    fun clearAllRecords()

    @Query("DELETE FROM records WHERE recordId=:id")
    fun removeRecord(id: Long?)

    @Query("SELECT * FROM records ORDER BY recordId DESC")
    fun getAllRecords(): LiveData<MutableList<RoomRecordItem>>

    @Query("SELECT COUNT(*) FROM records")
    fun getCountOfRecordsInDatabase():Int

}