package com.calendar.types

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.adapter.day.viewholder.DayViewHolder
import com.calendar.model.Day
import com.calendar.utils.setBackgroundFromDrawable

/**
 * @property onDayClickListener when day of month clicked this fun will invoke
 * @property dayBackground decision for day item background base on implementation of each class
 */
abstract class CalendarType {

    internal abstract val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit

    internal abstract fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
    )

    internal abstract fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean

    internal open fun bind(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) {
        dayBackground(viewHolder, day, properties)
    }

    internal fun checkAvailability(
        viewHolder: DayViewHolder,
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val availability = properties.availabilityRule.isAvailable(currentDay, properties)
        if (availability) setAvailableBackground(viewHolder)
        else setUnAvailableBackground(viewHolder)
        return availability
    }

    private fun setAvailableBackground(viewHolder: DayViewHolder) {
        viewHolder.bgDay.setBackgroundFromDrawable(
            R.drawable.bg_day
        )
        viewHolder.txtDay.alpha = 1f
        viewHolder.txtPrice.alpha = 1f
    }

    private fun setUnAvailableBackground(viewHolder: DayViewHolder) {
        viewHolder.bgDay.setBackgroundFromDrawable(
            id = R.drawable.bg_disabled_date,
            alpha = 0X3C
        )
        viewHolder.txtDay.alpha = 0.3f
        viewHolder.txtPrice.alpha = 0.3f
    }


}