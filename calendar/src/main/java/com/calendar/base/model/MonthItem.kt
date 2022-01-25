package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import java.util.*
import kotlin.collections.ArrayList

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int
) {

    private val days = ArrayList<DayItem>()

    //TODO maybe remove this
    fun getYear() = calendar.get(Calendar.YEAR)

    fun getDisplayedName(): String {
        return calendar.getDisplayedMonthName(month)
    }

    fun generateDays(): List<DayItem> {
        if (days.isNullOrEmpty()) {
            calendar.set(Calendar.MONTH, month)
            val dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (day in 1..dayOfMonth) {
                if (day == 1)
                    repeat(calendar.firstDayPositionInWeek()) {
                        days.add(DayItem(calendar, null))
                    }
                days.add(DayItem(calendar, day))
            }
        }
        return days
    }
}