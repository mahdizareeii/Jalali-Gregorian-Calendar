package com.calendar.base.types.multipleselection

import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarType
import com.calendar.utils.setBackgroundFromDrawable

class MultipleSelection(
    private val multipleSelectionListener: MultipleSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, dayItem, properties, listener ->
            if (checkAvailability(viewHolder, dayItem, properties)) {
                onDayClicked(properties, dayItem, multipleSelectionListener)
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
                    selectedDays = properties.selectedMultipleDayItem
                )
            )
        }
    }

    override fun isDaySelected(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean = properties.selectedMultipleDayItem.any {
        it == currentItem
    }

    private fun getDayBackground(
        currentItem: DayItem,
        selectedDays: ArrayList<DayItem>?
    ): Int {
        return if (selectedDays?.any { it == currentItem } == true) {
            R.drawable.bg_day_selected
        } else R.drawable.bg_day
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentItem: DayItem,
        listener: MultipleSelectionListener
    ) {
        property.apply {
            if (property.selectedMultipleDayItem.any { it == currentItem }) {
                property.selectedMultipleDayItem.remove(currentItem)
            } else {
                property.selectedMultipleDayItem.add(currentItem)
            }
            listener.onSelectedDays(property.selectedMultipleDayItem)
        }
    }
}