package com.calendar.base.types.singleselection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.DayItem
import com.calendar.CalendarProperties
import com.calendar.base.types.CalendarType

class SingleSelection(
    private val singleSelectionListener: SingleSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        dayItem: DayItem,
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
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, dayItem, properties)) {
            viewHolder.bgDay.background = ContextCompat.getDrawable(
                context, getDayBackground(
                    currentItem = dayItem,
                    selectedSingle = properties.selectedSingle
                )
            )
        }
    }

    private fun getDayBackground(
        currentItem: DayItem,
        selectedSingle: DayItem?
    ): Int {
        return if (selectedSingle != null) {
            when (selectedSingle) {
                currentItem -> R.drawable.bg_day_single_selected
                else -> R.drawable.bg_day
            }
        } else {
            R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentItem: DayItem,
        listener: SingleSelectionListener
    ) {
        property.apply {
            selectedSingle = currentItem
            listener.onDaySelected(currentItem)
        }
    }

}