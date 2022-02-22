package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.utils.setBackgroundFromDrawable
import com.calendar.utils.setMutableDrawableColor

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
        val startAgenda = getStartAgendaRange(day)
        val middleAgenda = getMiddleAgendaRange(day)
        val endAgenda = getEndAgendaRange(day)

        val isDaySelected = properties.calendarType.isDaySelected(
            day,
            properties
        )

        val startAgendaVisibility = !isDaySelected && startAgenda != null

        val middleAgendaVisibility = !isDaySelected && middleAgenda != null

        val endAgendaVisibility = !isDaySelected && endAgenda != null

        val background = when {
            startAgendaVisibility -> R.drawable.bg_day_dashed_stroke_start
            endAgendaVisibility -> R.drawable.bg_day_dashed_stroke_end
            middleAgendaVisibility -> R.drawable.bg_day_dashed_stroke_middle
            else -> return
        }

        viewHolder.imgStartAgenda.isVisible = startAgendaVisibility
        viewHolder.imgEndAgenda.isVisible = endAgendaVisibility
        imgStartAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
        imgEndAgenda.setMutableDrawableColor(
            endAgenda?.getAgendaColor()
        )
        viewHolder.bgDay.setBackgroundFromDrawable(background)
    }

    private fun getStartAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.any { range ->
            currentDay == range.startDate
        }
    }

    private fun getMiddleAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.any { range ->
            currentDay >= range.startDate && currentDay <= range.endDate
        }
    }

    private fun getEndAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.any { range ->
            currentDay == range.endDate
        }
    }
}