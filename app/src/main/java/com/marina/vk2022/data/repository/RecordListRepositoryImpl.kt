package com.marina.vk2022.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marina.vk2022.data.network.NetworkApi
import com.marina.vk2022.data.repository.mappers.MapperRecordItemToRecord
import com.marina.vk2022.data.storage.RecordStorage
import com.marina.vk2022.data.storage.room.mappers.MapperLiveDataRecordToLiveDataRecordItem
import com.marina.vk2022.domain.entity.RecordItem
import com.marina.vk2022.domain.repository.RecordListRepository

class RecordListRepositoryImpl(
    private val recordStorage: RecordStorage,
    private val networkApi: NetworkApi
) : RecordListRepository {

    private val shopListLiveData = MutableLiveData<List<RecordItem>>()
    private val shopList = sortedSetOf<RecordItem>({ p0, p1 -> p0.recordId.compareTo(p1.recordId) })

    override fun saveRecord(recordItem: RecordItem) {
        val record = MapperRecordItemToRecord.map(recordItem)
        recordStorage.saveRecord(record)
    }

    override fun deleteRecord(recordItem: RecordItem) {
        val record = MapperRecordItemToRecord.map(recordItem)
        recordStorage.deleteRecord(record)
    }

    override fun deleteRecord(recordItems: List<RecordItem>) {
        val records = MapperRecordItemToRecord.map(recordItems)
        recordStorage.deleteRecord(records)
    }

    override suspend fun getAllRecords(): LiveData<List<RecordItem>> {
        return MapperLiveDataRecordToLiveDataRecordItem.map(recordStorage.getAllRecords())
    }

    override fun update(recordItem: RecordItem) {
        TODO("Not yet implemented")
    }


}