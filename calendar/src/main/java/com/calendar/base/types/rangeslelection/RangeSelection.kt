package com.calendar.base.types.rangeslelection

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class RangeSelection(
    private val rangeSelectionListener: RangeSelectionListener
) : CalendarType() {

    override val onDayClickListener: (viewHolder: DayViewHolder, dayItem: DayItem, properties: CalendarProperties, listener: DaysAdapterListener) -> Unit
        get() = { viewHolder, dayItem, properties, listener ->
            if (checkAvailability(viewHolder, dayItem, properties)) {
                onDayClicked(properties, dayItem, rangeSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, dayItem, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(
                getDayBackground(
                    currentItem = dayItem,
                    checkIn = properties.selectedCheckIn,
                    checkOut = properties.selectedCheckOut
                )
            )
        }
    }

    override fun isDaySelected(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        properties.apply {
            if (selectedCheckIn != null && selectedCheckOut != null)
                return currentItem >= selectedCheckIn!! && currentItem <= selectedCheckOut!!

            if (selectedCheckIn != null)
                return currentItem == selectedCheckIn

            if (selectedCheckOut != null)
                return currentItem == selectedCheckOut
        }

        return false
    }

    private fun getDayBackground(
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
                        R.drawable.bg_day_selected
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
            if (showDaysPrice && selectedCheckIn == currentItem) {
                selectedCheckIn = null
                selectedCheckOut = null
            } else if (showDaysPrice && isCheckOutSelect() && currentItem == selectedCheckOut && currentItem.isDisable) {
                return
            } else if (selectedCheckIn == currentItem || selectedCheckIn == null || isCheckOutSelect()) {
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