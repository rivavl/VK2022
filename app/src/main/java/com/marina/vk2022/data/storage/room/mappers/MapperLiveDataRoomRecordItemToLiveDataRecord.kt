package com.marina.vk2022.data.storage.room.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marina.vk2022.data.storage.room.RoomRecordItem
import com.marina.vk2022.domain.entity.Record

object MapperLiveDataRoomRecordItemToLiveDataRecord {

    fun map(records: List<RoomRecordItem>): LiveData<List<Record>> {
        val recordList = records.map {
            Record(
                recordId = it.recordId,
                recordName = it.recordName,
                recordPath = it.recordPath,
                recordLength = it.recordLength,
                recordCreatingTime = it.recordCreatingTime
            )
        }
        val ld = MutableLiveData<List<Record>>()
        ld.value = recordList
        return ld
    }
}