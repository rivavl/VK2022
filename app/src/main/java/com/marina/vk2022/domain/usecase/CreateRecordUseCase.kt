package com.marina.vk2022.domain.usecase

import com.marina.vk2022.domain.entity.RecordItem
import com.marina.vk2022.domain.repository.RecordListRepository

class CreateRecordUseCase(private val recordListRepository: RecordListRepository) {

    operator fun invoke(recordItem: RecordItem) {
        return recordListRepository.saveRecord(recordItem)
    }

}