package com.calendar.types.withoutselection

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.viewholder.DayViewHolder
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class WithoutSelection(
    private val withoutSelectionListener: WithoutSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { _, _, _, _ -> }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, day, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(R.drawable.bg_day)
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean = false

}