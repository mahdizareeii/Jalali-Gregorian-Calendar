package com.calendar.base.types

import android.content.Context
import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.singleselection.SingleSelection

abstract class CalendarType {
    protected lateinit var context: Context

    internal open fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: CalendarListener
    ) {
        context = viewHolder.itemView.context
        backgroundAvailability(viewHolder, dayItem, properties)
        textColor(viewHolder, dayItem, properties)
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }

    private fun backgroundAvailability(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        if (!checkAvailability(dayItem, properties)) {
            viewHolder.bgDay.background.alpha = 200
            viewHolder.txtDay.alpha = 0.3f
        }
    }

    private fun textColor(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        viewHolder.txtDay.setTextColor(
            ContextCompat.getColor(
                context,
                when {
                    dayItem.isHoliday -> R.color.red
                    else -> {
                        if (isSelected(dayItem, properties)) R.color.white
                        else R.color.secondary
                    }
                }
            )
        )
    }

    protected fun checkAvailability(
        dayItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return properties.today != null && dayItem >= properties.today!!
    }

    private fun isSelected(
        dayItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return when (properties.calendarType::class.java) {
            RangeSelection::class.java -> {
                if (properties.selectedCheckIn != null && properties.selectedCheckOut != null)
                    return dayItem >= properties.selectedCheckIn!! && dayItem <= properties.selectedCheckOut!!

                if (properties.selectedCheckIn != null)
                    return dayItem == properties.selectedCheckIn

                if (properties.selectedCheckOut != null)
                    return dayItem == properties.selectedCheckOut

                return false
            }

            SingleSelection::class.java -> {
                return if (properties.selectedSingle != null)
                    properties.selectedSingle == dayItem
                else false
            }

            MultipleSelection::class.java -> {
                properties.selectedMultipleDayItem?.any {
                    it == dayItem
                } ?: false
            }

            else -> false
        }
    }
}