package com.marina.vk2022.data.storage.room.mappers

import com.marina.vk2022.data.storage.room.RoomRecordItem
import com.marina.vk2022.domain.entity.Record

object MapperRecordToRoomRecordItem {

    fun map(record: Record): RoomRecordItem {
        return RoomRecordItem(
            recordId = record.recordId,
            recordName = record.recordName,
            recordPath = record.recordPath,
            recordLength = record.recordLength,
            recordCreatingTime = record.recordCreatingTime
        )
    }
}