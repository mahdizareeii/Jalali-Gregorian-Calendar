package com.calendar.base.adapter.day.types.withoutselection

import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.types.DaySelectionType
import com.calendar.base.model.DayItem

internal class WithoutSelection : DaySelectionType {

    override fun bind(viewHolder: DayViewHolder, dayItem: DayItem) {
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }
}