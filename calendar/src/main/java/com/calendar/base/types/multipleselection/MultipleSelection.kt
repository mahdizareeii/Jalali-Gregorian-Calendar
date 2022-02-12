package com.calendar.base.types.multipleselection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType

class MultipleSelection(
    private val multipleSelectionListener: MultipleSelectionListener
) : CalendarType() {
    override fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) {
        super.bind(viewHolder, dayItem, properties, listener)
        if (checkAvailability(viewHolder, dayItem, properties)) {
            viewHolder.bgDay.background = ContextCompat.getDrawable(
                context,
                background(currentItem = dayItem, selectedDays = properties.selectedMultipleDayItem)
            )

            viewHolder.bgDay.setOnClickListener {
                onDayClicked(properties, dayItem, multipleSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }
    }

    private fun background(
        currentItem: DayItem,
        selectedDays: ArrayList<DayItem>?
    ): Int {
        return if (selectedDays?.any { it == currentItem } == true) {
            R.drawable.bg_day_single_selected
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