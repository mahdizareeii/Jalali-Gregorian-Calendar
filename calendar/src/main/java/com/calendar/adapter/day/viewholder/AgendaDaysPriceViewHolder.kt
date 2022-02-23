package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.utils.setMutableDrawableColor

internal class AgendaDaysPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayPriceViewHolder(view, properties, listener) {
    private val imgDayAgenda: AppCompatImageView = view.findViewById(R.id.img_day_agenda)

    override fun bind(day: Day) {
        super.bind(day)
        initAgendaDay(day)
    }

    private fun initAgendaDay(day: Day) {
        val startAgenda = getStartAgenda(day)
        imgDayAgenda.isVisible =
            !properties.calendarType.isDaySelected(day, properties) && startAgenda != null

        imgDayAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
    }

    private fun getStartAgenda(currentDay: Day) = properties.agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day -> day == currentDay } != null
    }
}