package com.calendar.base.types.rangeslelection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.DaySelectionProperties
import com.calendar.base.types.DaySelectionType

class RangeSelection() : DaySelectionType() {

    override fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        listener: DaysListener,
        properties: DaySelectionProperties,
        monthItemPosition: Int
    ) {
        super.bind(viewHolder, dayItem, listener, properties, monthItemPosition)

        viewHolder.bgDay.background = ContextCompat.getDrawable(
            context, background(
                currentItem = dayItem,
                checkIn = properties.selectedCheckIn,
                checkOut = properties.selectedCheckOut
            )
        )

        viewHolder.bgDay.setOnClickListener {
            if (properties.selectedCheckIn == null || properties.selectedCheckOut != null) {
                properties.selectedCheckIn = dayItem
                properties.selectedCheckOut = null
                listener.onDayNotifyDataSetChangedFrom(monthItemPosition)
            } else {
                properties.selectedCheckOut = dayItem
                listener.onDayNotifyDataSetChangedUntil(monthItemPosition)
            }
        }
    }

    private fun background(
        currentItem: DayItem,
        checkIn: DayItem?,
        checkOut: DayItem?,
    ): Int {
        return if (checkIn != null && checkOut != null) {
            when {
                checkIn == currentItem -> {
                    R.drawable.bg_day_range_start
                }
                checkOut == currentItem -> {
                    R.drawable.bg_day_range_end
                }
                else -> {
                    if (checkIn < currentItem && checkOut > currentItem)
                        R.drawable.bg_day_single_selected
                    else
                        R.drawable.bg_day
                }
            }
        } else {
            if (checkIn != null && checkIn == currentItem)
                R.drawable.bg_day_range_start
            else
                R.drawable.bg_day
        }
    }
}