package com.calendar.base.types.rangeslelection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarListener
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType

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
        if (checkAvailability(viewHolder, dayItem, properties)) {
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
        currentItem: DayItem,
        listener: RangeSelectionListener
    ) {
        property.apply {
            if (selectedCheckIn == currentItem || selectedCheckIn == null || selectedCheckOut != null) {
                selectedCheckIn = currentItem
                selectedCheckOut = null

                listener.onCheckInSelected(selectedCheckIn!!)
            } else {
                if (selectedCheckIn!! > currentItem) {
                    selectedCheckOut = selectedCheckIn
                    selectedCheckIn = currentItem
                    listener.onCheckInSelected(selectedCheckIn!!)
                    listener.onCheckOutSelected(selectedCheckOut!!)
                } else {
                    selectedCheckOut = currentItem
                    listener.onCheckOutSelected(selectedCheckOut!!)
                }
            }
        }
    }
}