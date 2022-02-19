package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import java.util.*

data class Year(
    val calendar: BaseCalendar,
    val year: Int,
) {
    /*fun getMonthOfYear(): List<MonthItem> {
        val months = ArrayList<MonthItem>()

        calendar.set(Calendar.YEAR, year)

        for (i in 0..11) months.add(MonthItem(calendar, i))

        return months
    }*/
}