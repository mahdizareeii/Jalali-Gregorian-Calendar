package com.calendar.base.types.singleselection

import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType

class SingleSelection(
    private val singleSelectionListener: SingleSelectionListener
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
                context, background(
                    currentItem = dayItem,
                    selectedSingle = properties.selectedSingle
                )
            )

            viewHolder.bgDay.setOnClickListener {
                onDayClicked(properties, dayItem, singleSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }
    }

    private fun background(
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