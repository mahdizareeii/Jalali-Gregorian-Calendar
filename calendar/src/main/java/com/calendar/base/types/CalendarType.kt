package com.calendar.base.types

import android.content.Context
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem

abstract class CalendarType {
    protected lateinit var context: Context

    internal open fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: CalendarListener
    ) {
        context = viewHolder.itemView.context
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }
}