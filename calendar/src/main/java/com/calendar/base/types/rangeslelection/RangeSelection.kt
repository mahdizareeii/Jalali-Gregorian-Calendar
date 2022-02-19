package com.calendar.base.types.rangeslelection

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.viewholder.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.Day
import com.calendar.base.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class RangeSelection(
    private val rangeSelectionListener: RangeSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, dayItem, properties, listener ->
            if (checkAvailability(viewHolder, dayItem, properties)) {
                onDayClicked(properties, dayItem, rangeSelectionListener)
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
                    checkIn = properties.selectedCheckIn,
                    checkOut = properties.selectedCheckOut
                )
            )
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        properties.apply {
            if (selectedCheckIn != null && selectedCheckOut != null)
                return currentDay >= selectedCheckIn!! && currentDay <= selectedCheckOut!!

            if (selectedCheckIn != null)
                return currentDay == selectedCheckIn

            if (selectedCheckOut != null)
                return currentDay == selectedCheckOut
        }

        return false
    }

    private fun getDayBackground(
        currentDay: Day,
        checkIn: Day?,
        checkOut: Day?,
    ): Int {
        return if (checkIn != null && checkOut != null) {
            when {
                checkIn == currentDay -> R.drawable.bg_day_range_start
                checkOut == currentDay -> R.drawable.bg_day_range_end
                else ->
                    if (checkIn < currentDay && checkOut > currentDay)
                        R.drawable.bg_day_selected
                    else R.drawable.bg_day
            }
        } else {
            if (checkIn != null && checkIn == currentDay)
                R.drawable.bg_day_range_start
            else R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentDay: Day,
        listener: RangeSelectionListener
    ) {
        property.apply {
            if (showDaysPrice && selectedCheckIn == currentDay) {
                selectedCheckIn = null
                selectedCheckOut = null
            } else if (showDaysPrice && isCheckOutSelect() && currentDay == selectedCheckOut && currentDay.isDisable) {
                return
            } else if (selectedCheckIn == currentDay || selectedCheckIn == null || isCheckOutSelect()) {
                selectedCheckIn = currentDay
                selectedCheckOut = null
                listener.onCheckInSelected(selectedCheckIn!!)
            } else {
                if (selectedCheckIn!! > currentDay) {
                    selectedCheckOut = selectedCheckIn
                    selectedCheckIn = currentDay
                    listener.onCheckInSelected(selectedCheckIn!!)
                    listener.onCheckOutSelected(selectedCheckOut!!)
                } else {
                    selectedCheckOut = currentDay
                    listener.onCheckOutSelected(selectedCheckOut!!)
                }
            }
        }
    }
}