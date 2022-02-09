package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import java.util.*

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int,
    val year: Int
) {
    private val days = ArrayList<DayItem>()

    var listener: MonthItemListener? = null

    init {
        calendar.clear()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.init()
    }

    val getYear get() = calendar.getYear()
    val getMonthName get() = calendar.getMonthName()

    fun generateDays(): List<DayItem> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1)
                        DayItem(null, null, null)
                    else
                        DayItem(
                            year = calendar.getYear(),
                            month = calendar.getMonth(),
                            day = it
                        )
                }
            )
        }
        return days
    }
}