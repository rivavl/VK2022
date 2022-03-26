package com.marina.vk2022.data.repository

import com.marina.vk2022.data.network.NetworkApi
import com.marina.vk2022.data.storage.RecordStorage
import com.marina.vk2022.domain.entity.RecordItem
import com.marina.vk2022.domain.repository.RecordListRepository

class RecordListRepositoryImpl(
    private val recordStorage: RecordStorage,
    private val networkApi: NetworkApi
) : RecordListRepository {

    override fun createRecord(): RecordItem {
        val record = recordStorage.createRecord()
        val recordItem = MapperRecordToRecordItem.map(record)
        return recordItem
    }

    override fun deleteRecord() {
        recordStorage.deleteRecord()
    }

    override fun playRecord() {
        recordStorage.playRecord()
    }
}