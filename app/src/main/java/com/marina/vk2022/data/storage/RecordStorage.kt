package com.marina.vk2022.data.storage

import androidx.lifecycle.LiveData
import com.marina.vk2022.domain.entity.Record

interface RecordStorage {

    fun saveRecord(record: Record)

    fun deleteRecord(record: Record)

    fun deleteRecord(record: List<Record>)

    suspend fun getAllRecords(): LiveData<List<Record>>
}