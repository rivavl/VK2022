package com.marina.vk2022.presentation.records_list_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.ListAdapter
import com.marina.vk2022.R
import com.marina.vk2022.domain.RecordItemDiffCallback
import com.marina.vk2022.domain.entity.RecordItem

class RecordListAdapter :
    ListAdapter<RecordItem, RecordItemViewHolder>(RecordItemDiffCallback()) {

    var onRecordItemLongClickListener: ((RecordItem) -> Unit)? = null
    var onRecordItemPlayClickListener: ((RecordItem) -> Unit)? = null
    var onRecordItemStopClickListener: ((RecordItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordItemViewHolder {
        val layout = R.layout.player_view
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return RecordItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecordItemViewHolder, position: Int) {
        val recordItem = getItem(position)

        viewHolder.view.setOnLongClickListener {
            onRecordItemLongClickListener?.invoke(recordItem)
            true
        }

        viewHolder.view.findViewById<AppCompatImageView>(R.id.record_play_button).setOnClickListener {
            onRecordItemPlayClickListener?.invoke(recordItem)
        }

        viewHolder.view.findViewById<AppCompatImageView>(R.id.record_pause_button).setOnClickListener {
            onRecordItemStopClickListener?.invoke(recordItem)
        }
        viewHolder.title.text = recordItem.recordName
        viewHolder.dateTime.text = recordItem.recordCreatingTime.toString()
    }
}