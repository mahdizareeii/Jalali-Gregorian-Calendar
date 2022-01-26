package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int
) {

    private val days = ArrayList<DayItem>()

    fun getYear() = calendar.getYearName()

    fun getDisplayedName(): String {
        return calendar.getDisplayedMonthName(month)
    }

    fun generateDays(): List<DayItem> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays(month).map {
                    if (it == -1)
                        DayItem(calendar, null)
                    else
                        DayItem(calendar, it)
                }
            )
        }
        return days
    }
}