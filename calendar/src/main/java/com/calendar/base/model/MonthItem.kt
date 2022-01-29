package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import java.util.*
import kotlin.collections.ArrayList

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int,
    val year: Int
) {

    init {
        calendar.clear()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
    }

    private val days = ArrayList<DayItem>()

    fun getYear() = calendar.getYearName()

    fun getDisplayedName(): String {
        return calendar.getDisplayedMonthName()
    }

    fun generateDays(): List<DayItem> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1)
                        DayItem(null)
                    else
                        DayItem(it)
                }
            )
        }
        return days
    }
}