package com.marina.vk2022.data.repository.mappers

import com.marina.vk2022.domain.entity.Record
import com.marina.vk2022.domain.entity.RecordItem

object MapperRecordToRecordItem {

    fun map(record: Record): RecordItem {
        return RecordItem(
            recordId = record.recordId,
            recordName = record.recordName,
            recordPath = record.recordPath,
            recordLength = record.recordLength,
            recordCreatingTime = record.recordCreatingTime
        )
    }
}