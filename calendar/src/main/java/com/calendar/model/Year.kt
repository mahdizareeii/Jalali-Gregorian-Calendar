package com.calendar.model

import com.calendar.calendar.BaseCalendar

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