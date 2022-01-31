package com.calendar.base.types

import com.calendar.base.model.DayItem

data class CalendarProperties(
    var today:DayItem? = null,

    //for range selection
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null,

    //for multiple selection

    //for single selection

    //for without selection
)