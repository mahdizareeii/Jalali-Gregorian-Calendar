package com.calendar.base.types.rangeslelection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.types.CalendarListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarType
import com.calendar.base.types.CalendarProperties

class RangeSelection(
    private val rangeSelectionListener: RangeSelectionListener
) : CalendarType() {

    override fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: CalendarListener
    ) {
        super.bind(viewHolder, dayItem, properties, listener)

        viewHolder.bgDay.background = ContextCompat.getDrawable(
            context, background(
                currentItem = dayItem,
                checkIn = properties.selectedCheckIn,
                checkOut = properties.selectedCheckOut
            )
        )

        viewHolder.bgDay.setOnClickListener {
            onDayClicked(properties, dayItem, rangeSelectionListener)
            listener.onNotifyDataSetChanged()
        }
    }

    private fun background(
        currentItem: DayItem,
        checkIn: DayItem?,
        checkOut: DayItem?,
    ): Int {
        return if (checkIn != null && checkOut != null) {
            when {
                checkIn == currentItem -> R.drawable.bg_day_range_start
                checkOut == currentItem -> R.drawable.bg_day_range_end
                else ->
                    if (checkIn < currentItem && checkOut > currentItem)
                        R.drawable.bg_day_single_selected
                    else R.drawable.bg_day
            }
        } else {
            if (checkIn != null && checkIn == currentItem)
                R.drawable.bg_day_range_start
            else R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        property: CalendarProperties,
        dayItem: DayItem,
        listener: RangeSelectionListener
    ) {
        property.apply {
            if (selectedCheckIn == dayItem || selectedCheckIn == null || selectedCheckOut != null) {
                selectedCheckIn = dayItem
                selectedCheckOut = null

                listener.onCheckInSelected(selectedCheckIn!!)
            } else {
                if (selectedCheckIn!! > dayItem) {
                    selectedCheckOut = selectedCheckIn
                    selectedCheckIn = dayItem
                    listener.onCheckInSelected(selectedCheckIn!!)
                    listener.onCheckOutSelected(selectedCheckOut!!)
                } else {
                    selectedCheckOut = dayItem
                    listener.onCheckOutSelected(selectedCheckOut!!)
                }
            }
        }
    }
}