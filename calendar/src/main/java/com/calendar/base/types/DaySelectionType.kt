package com.calendar.base.types

import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem

internal interface DaySelectionType {
    fun bind(viewHolder: DayViewHolder, dayItem: DayItem)
}