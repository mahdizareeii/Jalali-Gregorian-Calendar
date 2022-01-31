package com.calendar.base.types

import com.calendar.base.model.DayItem

data class CalendarProperties(
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null
)