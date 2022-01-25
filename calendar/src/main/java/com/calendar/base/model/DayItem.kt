package com.calendar.base.model

import android.view.View
import com.calendar.base.calendar.BaseCalendar

data class DayItem(
    val calendar: BaseCalendar,
    val day: Int?
) {
    val visibility = if (day != null) View.VISIBLE else View.INVISIBLE
}