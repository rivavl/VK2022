package com.marina.vk2022.data.storage.room.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marina.vk2022.domain.entity.Record
import com.marina.vk2022.domain.entity.RecordItem

object MapperLiveDataRecordToLiveDataRecordItem {

    fun map(records: LiveData<List<Record>>): LiveData<List<RecordItem>> {
        val list = records.value?.toList()
        val recordList =  list?.map {
            RecordItem(
                recordId = it.recordId,
                recordName = it.recordName,
                recordPath = it.recordPath,
                recordLength = it.recordLength,
                recordCreatingTime = it.recordCreatingTime
            )
        }
        val ld = MutableLiveData<List<RecordItem>>()
        ld.value = recordList!!
        return ld
    }
}