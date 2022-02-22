package com.calendar.base.types.withoutselection

import androidx.core.content.ContextCompat
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class WithoutSelection(
    private val withoutSelectionListener: WithoutSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { _, _, _, _ -> }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, dayItem, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(R.drawable.bg_day)
        }
    }

    override fun isDaySelected(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean = false

}