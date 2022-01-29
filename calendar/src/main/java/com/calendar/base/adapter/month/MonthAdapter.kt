package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calendar.R
import com.calendar.base.adapter.day.types.DaySelectionType
import com.calendar.base.model.MonthItem

internal class MonthAdapter(
    private val daySelectionType: DaySelectionType
) : ListAdapter<MonthItem, MonthViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            ),
            daySelectionType = daySelectionType
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtilCallBack : DiffUtil.ItemCallback<MonthItem>() {
        override fun areItemsTheSame(oldItem: MonthItem, newItem: MonthItem): Boolean {
            return oldItem.month == newItem.month
        }

        override fun areContentsTheSame(oldItem: MonthItem, newItem: MonthItem): Boolean {
            return oldItem == newItem
        }
    }
}