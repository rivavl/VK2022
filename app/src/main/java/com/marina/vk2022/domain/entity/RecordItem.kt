package com.marina.vk2022.domain.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/*
* Модель для бизнес логики
* */

data class RecordItem(
    val recordId:Long = 0L,
    var recordName:String ="",
    var recordPath:String ="",
    var recordLength:Long = 0L,
    var recordCreatingTime:Long=0L
)