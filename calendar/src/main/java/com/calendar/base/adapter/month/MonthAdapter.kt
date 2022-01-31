package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calendar.R
import com.calendar.base.types.CalendarListener
import com.calendar.base.model.MonthItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType

internal class MonthAdapter(
    private val calendarProperties: CalendarProperties
) : ListAdapter<MonthItem, MonthViewHolder>(DiffUtilCallBack()), CalendarListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            ),
            calendarProperties = calendarProperties,
            calendarListener = this
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onNotifyDataSetChanged() {
        for (indic in currentList.indices)
            getItem(indic).listener?.onDataSetChanged()
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