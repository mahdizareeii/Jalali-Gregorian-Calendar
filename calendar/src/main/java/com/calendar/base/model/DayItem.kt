package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import java.util.*

data class DayItem(
    val calendar: BaseCalendar,
    val day: Int?
) {
}