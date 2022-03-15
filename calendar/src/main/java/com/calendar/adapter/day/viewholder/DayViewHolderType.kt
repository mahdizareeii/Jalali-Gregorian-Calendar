package com.calendar.adapter.day.viewholder

import com.calendar.R

internal enum class DayViewHolderType(val layoutId: Int) {
    DayViewHolder(R.layout.day_item_calendar),
    DayPriceViewHolder(R.layout.day_item_calendar),
    AgendaDaysPriceViewHolder(R.layout.day_item_calendar_agenda),
    AgendaRangeDaysViewHolder(R.layout.day_item_calendar_agenda_range),
    Unknown(0)
}