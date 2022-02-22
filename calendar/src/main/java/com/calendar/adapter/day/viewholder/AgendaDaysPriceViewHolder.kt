package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.utils.setMutableDrawableColor

internal class AgendaDaysPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayPriceViewHolder(view, properties, listener) {

    override fun bind(day: Day) {
        super.bind(day)
        initAgendaDay(this, day)
    }

    private fun initAgendaDay(
        viewHolder: DayViewHolder,
        day: Day
    ) {
        viewHolder.imgStartAgenda.isVisible =
            !properties.calendarType.isDaySelected(day, properties) && imgStartAgendaVisibility(day)

        viewHolder.imgEndAgenda.isVisible = false

        viewHolder.imgStartAgenda.setMutableDrawableColor(
            imgStartAgendaColor(day)
        )
    }

    private fun imgStartAgendaVisibility(currentDay: Day) = properties.agendaDays.any {
        it.agendaList.any { day -> day == currentDay }
    }

    private fun imgStartAgendaColor(currentDay: Day) = properties.agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day -> day == currentDay } != null
    }?.getAgendaColor()
}