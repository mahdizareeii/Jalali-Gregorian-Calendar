package com.calendar.base.types

import com.calendar.base.model.DayItem

data class CalendarProperties(
    val calendarType: CalendarType,
    var today: DayItem? = null,

    //for range selection
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null,

    //for multiple selection
    val selectedMultipleDayItem: ArrayList<DayItem>? = null,

    //for single selection
    var selectedSingle: DayItem? = null

    //for without selection
)