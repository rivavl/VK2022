package com.marina.vk2022.data.storage

import com.marina.vk2022.domain.entity.Record

interface RecordStorage {

    fun createRecord(): Record

    fun deleteRecord()

    fun playRecord()
}