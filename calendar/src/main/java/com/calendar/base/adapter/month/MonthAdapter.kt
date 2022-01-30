package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calendar.R
import com.calendar.base.adapter.day.DaysListener
import com.calendar.base.model.MonthItem
import com.calendar.base.types.DaySelectionProperties
import com.calendar.base.types.DaySelectionType

internal class MonthAdapter(
    private val daySelectionType: DaySelectionType,
    private val daySelectionProperties: DaySelectionProperties
) : ListAdapter<MonthItem, MonthViewHolder>(DiffUtilCallBack()), DaysListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            ),
            daySelectionType = daySelectionType,
            daySelectionProperties = daySelectionProperties,
            daysListener = this
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private var from = 0
    override fun onDayNotifyDataSetChangedFrom(position: Int) {
        getItem(position).listener.onDataSetChanged()
        from = position
    }

    override fun onDayNotifyDataSetChangedUntil(position: Int) {
        for (i in from..position)
            getItem(i).listener.onDataSetChanged()
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