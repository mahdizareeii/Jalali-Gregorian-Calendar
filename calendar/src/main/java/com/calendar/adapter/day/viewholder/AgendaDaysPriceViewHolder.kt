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
        val startAgenda = getStartAgenda(day)
        viewHolder.imgStartAgenda.isVisible =
            !properties.calendarType.isDaySelected(day, properties) && startAgenda != null

        viewHolder.imgEndAgenda.isVisible = false

        viewHolder.imgStartAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
    }

    private fun getStartAgenda(currentDay: Day) = properties.agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day -> day == currentDay } != null
    }
}