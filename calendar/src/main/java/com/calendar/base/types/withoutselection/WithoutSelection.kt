package com.calendar.base.types.withoutselection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType

class WithoutSelection(
    private val withoutSelectionListener: WithoutSelectionListener
) : CalendarType() {

    override fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) {
        super.bind(viewHolder, dayItem, properties, listener)
        if (checkAvailability(viewHolder, dayItem, properties)) {
            viewHolder.bgDay.background = ContextCompat.getDrawable(
                context, R.drawable.bg_day
            )
        }
    }

}