package com.marina.vk2022.data.network

import com.marina.vk2022.domain.entity.Record

//не успела :(

interface NetworkApi {

    fun createRecord(): Record

    fun deleteRecord()
}