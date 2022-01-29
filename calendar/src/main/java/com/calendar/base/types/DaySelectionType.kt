package com.calendar.base.types

import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem

internal abstract class DaySelectionType {
    open fun bind(viewHolder: DayViewHolder, dayItem: DayItem) {
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }
}