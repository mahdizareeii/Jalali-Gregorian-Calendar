package com.calendar.types.singleselection

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.viewholder.DayViewHolder
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class SingleSelection(
    private val singleSelectionListener: SingleSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, dayItem, properties, listener ->
            if (checkAvailability(viewHolder, dayItem, properties)) {
                onDayClicked(properties, dayItem, singleSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, day, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(
                getDayBackground(
                    currentDay = day,
                    selectedSingle = properties.selectedSingle
                )
            )
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean = properties.selectedSingle != null && properties.selectedSingle == currentDay

    private fun getDayBackground(
        currentDay: Day,
        selectedSingle: Day?
    ): Int {
        return if (selectedSingle != null) {
            when (selectedSingle) {
                currentDay -> R.drawable.bg_day_single_selected
                else -> R.drawable.bg_day
            }
        } else {
            R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentDay: Day,
        listener: SingleSelectionListener
    ) {
        property.apply {
            if (selectedSingle == currentDay) {
                selectedSingle = null
                listener.onDaySelected(null)
            } else {
                selectedSingle = currentDay
                listener.onDaySelected(currentDay)
            }
        }
    }

}