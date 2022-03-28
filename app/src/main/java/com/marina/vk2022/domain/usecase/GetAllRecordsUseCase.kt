package com.marina.vk2022.domain.usecase

import androidx.lifecycle.LiveData
import com.marina.vk2022.domain.entity.RecordItem
import com.marina.vk2022.domain.repository.RecordListRepository

class GetAllRecordsUseCase(private val recordListRepository: RecordListRepository) {

    suspend operator fun invoke(): LiveData<List<RecordItem>> {
        return recordListRepository.getAllRecords()
    }

}
