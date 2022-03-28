package com.marina.vk2022.domain.entity

/*
* Модель для стореджа
* */

data class Record(
    val recordId: Long = 0L,
    var recordName: String = "",
    var recordPath: String = "",
    var recordLength: String = "",
    var recordCreatingTime: Long = 0L
)