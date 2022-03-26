package com.marina.vk2022.domain.usecase

import com.marina.vk2022.domain.repository.RecordListRepository

class PlayRecordUseCase(private val recordListRepository:RecordListRepository) {

    operator fun invoke() {
        recordListRepository.playRecord()
    }

}