package com.marina.vk2022.data.storage.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.marina.vk2022.data.storage.RecordStorage
import com.marina.vk2022.data.storage.room.mappers.MapperLiveDataRoomRecordItemToLiveDataRecord
import com.marina.vk2022.data.storage.room.mappers.MapperRecordItemToRoomRecordItem
import com.marina.vk2022.data.storage.room.mappers.MapperRecordToRoomRecordItem
import com.marina.vk2022.domain.entity.Record
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RoomRecordStorage(private val context: Context) : RecordStorage {

    private val dao = RecordDatabase.getInstance(context).recordDatabaseDao

    override fun saveRecord(record: Record) {
        val recordItem = MapperRecordToRoomRecordItem.map(record)
        GlobalScope.launch { dao.insertRecord(recordItem) }
    }

    override fun deleteRecord(record: Record) {
        val recordItem = MapperRecordItemToRoomRecordItem.map(record)
        GlobalScope.launch { dao.removeRecord(recordItem) }
    }

    override fun deleteRecord(record: List<Record>) {
        val recordItem = MapperRecordItemToRoomRecordItem.map(record)
        GlobalScope.launch {
            dao.removeRecord(recordItem)
        }
    }

    override suspend fun getAllRecords(): LiveData<List<Record>> {
        val allRecords: LiveData<List<Record>> =
            MapperLiveDataRoomRecordItemToLiveDataRecord.map(dao.getAllRecords())
        Log.e("getAllRecords: ", allRecords.toString())
        return allRecords
    }
}