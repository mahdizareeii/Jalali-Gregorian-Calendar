package com.calendar.base.adapter.day.types

import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem

internal interface DaySelectionType {
    fun bind(viewHolder: DayViewHolder, dayItem: DayItem)
}