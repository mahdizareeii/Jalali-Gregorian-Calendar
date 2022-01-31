package com.calendar.base.types

import android.content.Context
import androidx.core.content.ContextCompat
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem

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
        textColor(viewHolder, dayItem)
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
    ) {
        viewHolder.txtDay.setTextColor(
            ContextCompat.getColor(
                context,
                if (dayItem.isHoliday) R.color.red
                else R.color.secondary
            )
        )
    }

    protected fun checkAvailability(
        dayItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return properties.today != null && dayItem >= properties.today!!
    }
}