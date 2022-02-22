package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.utils.setBackgroundFromDrawable

internal class AgendaRangeDaysViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayViewHolder(view, properties, listener) {
    override fun bind(day: Day) {
        super.bind(day)
        initRangeAgendaDays(this, day)
    }

    private fun initRangeAgendaDays(
        viewHolder: DayViewHolder,
        day: Day
    ) {
        val start = !properties.calendarType.isDaySelected(
            day,
            properties
        ) && startAgendaRangeVisibility(day)

        val middle = !properties.calendarType.isDaySelected(
            day,
            properties
        ) && middleAgendaRangeVisibility(day)

        val end = !properties.calendarType.isDaySelected(
            day,
            properties
        ) && endAgendaRangeVisibility(day)

        val background = when {
            start -> R.drawable.bg_day_dashed_stroke_start
            middle -> R.drawable.bg_day_dashed_stroke_middle
            end -> R.drawable.bg_day_dashed_stroke_end
            else -> return
        }

        viewHolder.imgStartAgenda.isVisible = start
        viewHolder.imgEndAgenda.isVisible = end
        viewHolder.bgDay.setBackgroundFromDrawable(background)
    }

    private fun startAgendaRangeVisibility(currentDay: Day) = properties.agendaRangeDays.any {
        it.agendaRangeList.any { range ->
            currentDay == range.startDate
        }
    }

    private fun middleAgendaRangeVisibility(currentDay: Day) = properties.agendaRangeDays.any {
        it.agendaRangeList.any { range ->
            currentDay >= range.startDate && currentDay <= range.endDate
        }
    }

    private fun endAgendaRangeVisibility(currentDay: Day) = properties.agendaRangeDays.any {
        it.agendaRangeList.any { range ->
            currentDay == range.endDate
        }
    }
}