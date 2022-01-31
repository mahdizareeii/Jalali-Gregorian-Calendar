package com.calendar.base.types

import android.content.Context
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysListener
import com.calendar.base.model.DayItem

abstract class DaySelectionType {
    protected lateinit var context: Context

    open fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: DaySelectionProperties,
        listener: DaysListener
    ) {
        context = viewHolder.itemView.context
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }
}