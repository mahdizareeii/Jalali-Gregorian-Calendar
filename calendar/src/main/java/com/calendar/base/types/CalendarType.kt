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
        textColor(viewHolder, dayItem, properties)
        viewHolder.bgDay.visibility = dayItem.visibility
        viewHolder.txtDay.text = dayItem.day.toString()
    }

    private fun textColor(
        viewHolder: DayViewHolder,
        currentItem: DayItem,
        properties: CalendarProperties
    ) {
        viewHolder.txtDay.setTextColor(
            ContextCompat.getColor(
                context,
                when {
                    currentItem.isHoliday -> R.color.red
                    else -> {
                        if (isSelected(currentItem, properties)) R.color.white
                        else R.color.secondary
                    }
                }
            )
        )
    }

    internal fun checkAvailability(
        viewHolder: DayViewHolder,
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        val isAvailable = if (properties.getToday() == null) true
        else currentItem >= properties.getToday()!!

        if (!isAvailable) setUnAvailableBackground(viewHolder)

        return isAvailable
    }

    private fun setUnAvailableBackground(viewHolder: DayViewHolder) {
        viewHolder.bgDay.background.alpha = 200
        viewHolder.txtDay.alpha = 0.3f
    }

    private fun isSelected(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return when (properties.calendarType::class.java) {
            RangeSelection::class.java -> {
                if (properties.selectedCheckIn != null && properties.selectedCheckOut != null)
                    return currentItem >= properties.selectedCheckIn!! && currentItem <= properties.selectedCheckOut!!

                if (properties.selectedCheckIn != null)
                    return currentItem == properties.selectedCheckIn

                if (properties.selectedCheckOut != null)
                    return currentItem == properties.selectedCheckOut

                return false
            }

            SingleSelection::class.java -> {
                return if (properties.selectedSingle != null)
                    properties.selectedSingle == currentItem
                else false
            }

            MultipleSelection::class.java -> {
                properties.selectedMultipleDayItem?.any {
                    it == currentItem
                } ?: false
            }

            else -> false
        }
    }
}