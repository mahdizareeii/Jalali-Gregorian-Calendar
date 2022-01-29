package com.calendar.base.types.withoutselection

import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.types.DaySelectionType
import com.calendar.base.model.DayItem

internal class WithoutSelection : DaySelectionType() {

    override fun bind(viewHolder: DayViewHolder, dayItem: DayItem) {
        super.bind(viewHolder, dayItem)
    }
}