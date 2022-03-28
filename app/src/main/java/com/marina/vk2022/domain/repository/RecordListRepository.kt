package com.marina.vk2022.domain.repository

import androidx.lifecycle.LiveData
import com.marina.vk2022.domain.entity.RecordItem

interface RecordListRepository {

    fun saveRecord(recordItem: RecordItem)

    fun deleteRecord(recordItem: RecordItem)

    fun deleteRecord(recordItems: List<RecordItem>)

    suspend fun getAllRecords(): LiveData<List<RecordItem>>

    fun update(recordItem: RecordItem)

}