package com.marina.vk2022.data.repository.mappers

import com.marina.vk2022.domain.entity.Record
import com.marina.vk2022.domain.entity.RecordItem

object MapperRecordItemToRecord {

    fun map(recordItem: RecordItem): Record {
        return Record(
            recordId = recordItem.recordId,
            recordName = recordItem.recordName,
            recordPath = recordItem.recordPath,
            recordLength = recordItem.recordLength,
            recordCreatingTime = recordItem.recordCreatingTime
        )
    }

    fun map(recordItem: List<RecordItem>): List<Record> {
        return recordItem.map { Record(
            recordId = it.recordId,
            recordName = it.recordName,
            recordPath = it.recordPath,
            recordLength = it.recordLength,
            recordCreatingTime = it.recordCreatingTime
            )
        }
    }
}