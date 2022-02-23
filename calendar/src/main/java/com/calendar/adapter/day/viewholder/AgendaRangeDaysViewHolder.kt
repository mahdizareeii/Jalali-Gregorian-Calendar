package com.calendar.adapter.day.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
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
    private val bgAgendaDayRange: FrameLayout = view.findViewById(R.id.bg_agenda_day_range)
    private val imgStartAgenda: AppCompatImageView = view.findViewById(R.id.img_start_day_agenda)
    private val imgEndAgenda: AppCompatImageView = view.findViewById(R.id.img_end_day_agenda)

    override fun bind(day: Day) {
        super.bind(day)
        initRangeAgendaDays(day)
    }

    private fun initRangeAgendaDays(day: Day) {
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

        val isAgendaRange = startAgendaVisibility || middleAgendaVisibility || endAgendaVisibility

        val background = when {
            startAgendaVisibility -> R.drawable.bg_day_dashed_stroke_start
            middleAgendaVisibility -> R.drawable.bg_day_dashed_stroke_middle
            endAgendaVisibility -> R.drawable.bg_day_dashed_stroke_end
            else -> return
        }

        if (isAgendaRange) txtDay.setTypeface(txtDay.typeface, Typeface.BOLD)

        imgStartAgenda.isVisible = startAgendaVisibility
        imgEndAgenda.isVisible = endAgendaVisibility
        imgStartAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
        imgEndAgenda.setMutableDrawableColor(
            endAgenda?.getAgendaColor()
        )
        bgAgendaDayRange.setBackgroundFromDrawable(background)
    }

    private fun getStartAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay == range.startDate
        } != null
    }

    private fun getMiddleAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay > range.startDate && currentDay < range.endDate
        } != null
    }

    private fun getEndAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay == range.endDate
        } != null
    }
}