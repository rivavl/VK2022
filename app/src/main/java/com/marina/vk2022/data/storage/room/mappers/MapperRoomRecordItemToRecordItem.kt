package com.marina.vk2022.data.storage.room.mappers

import com.marina.vk2022.domain.entity.Record
import com.marina.vk2022.domain.entity.RecordItem

object MapperRoomRecordItemToRecordItem {

    fun map(recordItem: RecordItem): Record {
        return Record(
            recordId = recordItem.recordId,
            recordName = recordItem.recordName,
            recordPath = recordItem.recordPath,
            recordLength = recordItem.recordLength,
            recordCreatingTime = recordItem.recordCreatingTime
        )
    }
}