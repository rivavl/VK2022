package com.marina.vk2022.data.storage.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RoomRecordItem(
    @PrimaryKey(autoGenerate = true)
    val recordId: Long = 0L,
    @ColumnInfo(name = "record_name")
    var recordName: String = "",
    @ColumnInfo(name = "record_path")
    var recordPath: String = "",
    @ColumnInfo(name = "record_length")
    var recordLength: Long = 0L,
    @ColumnInfo(name = "record_creating_time")
    var recordCreatingTime: Long = 0L
)