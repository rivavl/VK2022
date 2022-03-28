package com.marina.vk2022.domain

import androidx.recyclerview.widget.DiffUtil
import com.marina.vk2022.domain.entity.RecordItem

class RecordItemDiffCallback : DiffUtil.ItemCallback<RecordItem>() {
    override fun areItemsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
        return oldItem.recordId == newItem.recordId
    }

    override fun areContentsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
        return oldItem == newItem
    }
}