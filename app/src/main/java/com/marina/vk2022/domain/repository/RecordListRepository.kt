package com.marina.vk2022.domain.repository

import com.marina.vk2022.domain.entity.RecordItem

interface RecordListRepository {

    fun createRecord(): RecordItem

    fun deleteRecord()

    fun playRecord()

}