package com.marina.vk2022.presentation.records_list_recycler_view

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.marina.vk2022.R

class RecordItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<AppCompatTextView>(R.id.record_title)
    val dateTime = view.findViewById<AppCompatTextView>(R.id.record_date_and_time)
}