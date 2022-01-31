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

    /**
     * for notify months when select range
     */
    private var fromMonth = 0
    private var untilMonth = 0

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

    override fun onDayNotifyDataSetChangedFrom(position: Int) {
        for (i in fromMonth..untilMonth) getItem(i).listener?.onDataSetChanged()
        getItem(position).listener?.onDataSetChanged()
        fromMonth = position
    }

    override fun onDayNotifyDataSetChangedUntil(position: Int) {
        for (i in fromMonth..position) getItem(i).listener?.onDataSetChanged()
        untilMonth = position
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